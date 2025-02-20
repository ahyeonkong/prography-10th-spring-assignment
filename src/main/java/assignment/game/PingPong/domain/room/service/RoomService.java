package assignment.game.PingPong.domain.room.service;

import assignment.game.PingPong.domain.room.dto.RoomDetailResponse;
import assignment.game.PingPong.domain.room.dto.RoomResponse;
import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.room.entity.RoomType;
import assignment.game.PingPong.domain.room.repository.RoomRepository;
import assignment.game.PingPong.domain.room.entity.RoomStatus;
import assignment.game.PingPong.domain.user.entity.Status;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.user.repository.UserRepository;
import assignment.game.PingPong.domain.userRoom.entity.Team;
import assignment.game.PingPong.domain.userRoom.entity.UserRoom;
import assignment.game.PingPong.domain.userRoom.repository.UserRoomRepository;
import assignment.game.PingPong.global.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository, UserRoomRepository userRoomRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
    }

    @Async
    public void scheduleFinishGame(Room room) {
        try {
            Thread.sleep(60000); // 1분 대기
            room.setStatus(RoomStatus.FINISH); // 방 상태를 FINISH로 변경
            roomRepository.save(room); // 변경된 상태 저장
        } catch (InterruptedException e) { // 비동기 작업 내에서 발생한 예외를 RuntimeException으로 던지고, 호출한 메서드에서 이를 처리
            throw new RuntimeException("에러가 발생했습니다.", e);
        }
    }

    public ApiResponse<Void> createRoom(int userId, String roomType, String title) {
        // 유저 조회
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ApiResponse.invalidRequest(); // 유효하지 않은 유저 ID에 대한 응답
        }

        // 유저 상태 확인
        if (!user.getStatus().equals(Status.ACTIVE)) {
            return ApiResponse.invalidRequest(); // 유저가 활성 상태가 아님
        }

        // 유저가 이미 호스트로 설정된 방이 있는지 확인
        if (roomRepository.existsByHost(user)) {
            return ApiResponse.invalidRequest(); // 이미 호스트로 설정된 방이 있음
        }

        // 새로운 방 생성 및 초기화
        Room room = new Room();
        room.setHost(user);
        room.setRoomType(RoomType.valueOf(roomType));
        room.setTitle(title);
        room.setStatus(RoomStatus.WAIT); // 초기 상태: 대기(WAIT)
        room.setCreatedAt(LocalDateTime.now().withNano(0));
        room.setUpdatedAt(LocalDateTime.now().withNano(0));

        room = roomRepository.save(room);

        // UserRoom 테이블에 호스트 데이터 추가
        UserRoom userRoom = new UserRoom();
        userRoom.setUser(user);
        userRoom.setRoom(room);
        userRoom.setTeam(Team.RED); // 기본적으로 RED 팀에 배정 (호스트)
        userRoomRepository.save(userRoom);

        return ApiResponse.success(null); // 성공 응답 반환
    }

    public ApiResponse<RoomResponse> getAllRooms(int page, int size) {
        // 페이징 및 정렬 설정 (id 기준 오름차순)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Room> roomPage = roomRepository.findAll(pageRequest);

        // Room 데이터를 RoomDetail로 변환
        List<RoomResponse.RoomDetail> roomList = roomPage.getContent().stream().map(room ->
                new RoomResponse.RoomDetail(
                        room.getId(),
                        room.getTitle(),
                        room.getHost().getId(),
                        room.getRoomType().toString(),
                        room.getStatus().name()
                )
        ).collect(Collectors.toList());

        // RoomResponse 생성
        RoomResponse response = new RoomResponse(
                (int) roomPage.getTotalElements(),
                roomPage.getTotalPages(),
                roomList
        );

        // ApiResponse로 성공 응답 반환
        return ApiResponse.success(response);
    }

    public ApiResponse<RoomDetailResponse> getRoomDetail(int roomId) {
        // 방 조회
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ApiResponse.invalidRequest(); // 존재하지 않는 방 ID에 대한 응답
        }

        // 날짜 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Room 엔티티를 RoomDetailResponse로 변환
        RoomDetailResponse response = new RoomDetailResponse(
                room.getId(),
                room.getTitle(),
                room.getHost().getId(),
                room.getRoomType().toString(),
                room.getStatus().name(),
                room.getCreatedAt().format(formatter),
                room.getUpdatedAt().format(formatter)
        );

        return ApiResponse.success(response); // 성공 응답 반환
    }

    public ApiResponse<Void> joinRoom(int roomId, int userId) {
        // 방 조회
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ApiResponse.invalidRequest(); // 존재하지 않는 방 ID
        }

        // 방 상태 확인
        if (!room.getStatus().equals(RoomStatus.WAIT)) {
            return ApiResponse.invalidRequest(); // 대기 상태가 아닌 방에는 참가 불가
        }

        // 유저 조회
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !user.getStatus().equals(Status.ACTIVE)) {
            return ApiResponse.invalidRequest(); // 유효하지 않은 유저이거나 비활성 상태인 유저
        }

        // 유저가 이미 다른 방에 참여 중인지 확인
        if (userRoomRepository.existsByUser(user)) {
            return ApiResponse.invalidRequest(); // 이미 다른 방에 참여 중인 유저
        }

        // 방 정원 확인 (SINGLE: 최대 2명, DOUBLE: 최대 4명)
        int maxCapacity = room.getRoomType().equals(RoomType.SINGLE) ? 2 : 4;
        long currentCapacity = userRoomRepository.countByRoom(room);
        if (currentCapacity >= maxCapacity) {
            return ApiResponse.invalidRequest(); // 방 정원이 가득 찼음
        }

        // 팀 배정 로직 (RED 우선 배정)
        Team assignedTeam = assignTeam(room);

        // RED와 BLUE의 인원을 각각 확인하여 초과 여부를 체크
        long redCount = userRoomRepository.countByTeamAndRoom(Team.RED, room);
        long blueCount = userRoomRepository.countByTeamAndRoom(Team.BLUE, room);

        if (room.getRoomType() == RoomType.DOUBLE) {
            if ((assignedTeam == Team.RED && redCount >= 2) ||
                    (assignedTeam == Team.BLUE && blueCount >= 2)) {
                return ApiResponse.invalidRequest(); // 팀 인원이 초과된 경우
            }
        }

        // UserRoom 테이블에 데이터 저장
        UserRoom userRoom = new UserRoom();
        userRoom.setUser(user);
        userRoom.setRoom(room);
        userRoom.setTeam(assignedTeam);
        userRoomRepository.save(userRoom);

        return ApiResponse.success(null); // 성공 응답 반환
    }

    private Team assignTeam(Room room) {
        RoomType roomType = room.getRoomType(); // 방의 타입 가져오기
        long redCount = userRoomRepository.countByTeamAndRoom(Team.RED, room);
        long blueCount = userRoomRepository.countByTeamAndRoom(Team.BLUE, room);

        if (roomType == RoomType.SINGLE) {
            // SINGLE: RED-BLUE 순서로 배정 (1:1)
            return redCount <= blueCount ? Team.RED : Team.BLUE;
        } else if (roomType == RoomType.DOUBLE) {
            // DOUBLE: RED-RED-BLUE-BLUE 순서로 배정 (2:2)
            if (redCount < 2) { // RED가 2명이 될 때까지 RED에 배정
                return Team.RED;
            } else {
                return Team.BLUE; // 이후 BLUE에 배정
            }
        }
        return null;
    }

    // 방 나가기
    @Transactional
    public ApiResponse<Void> leaveRoom(int roomId, int userId) {
        // 1. 방 존재 여부 확인
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ApiResponse.invalidRequest(); // 존재하지 않는 방
        }

        // 2. 유저 존재 여부 확인
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ApiResponse.invalidRequest(); // 존재하지 않는 유저
        }

        // 3. 유저가 해당 방에 참가 중인지 확인
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room);
        if (userRoom == null) {
            return ApiResponse.invalidRequest(); // 참가하지 않은 상태
        }

        // 4. 방 상태 확인 (PROGRESS 또는 FINISH 상태인 경우 나갈 수 없음)
        if (room.getStatus() == RoomStatus.PROGRESS || room.getStatus() == RoomStatus.FINISH) {
            return ApiResponse.invalidRequest();
        }

        // 5. 호스트 여부 확인 및 처리
        if (room.getHost() != null && room.getHost().getId()==(user.getId())) {
            // 호스트가 방을 나가는 경우
            userRoomRepository.deleteByRoom(room); // 방에 있는 모든 유저 삭제
            room.setStatus(RoomStatus.FINISH); // 방 상태를 FINISH로 변경
            return ApiResponse.success(null); // 성공 응답 반환
        }

        // 6. 일반 유저가 나가는 경우 처리
        userRoomRepository.delete(userRoom);
        return ApiResponse.success(null); // 성공 응답 반환
    }

    // 게임 시작
    @Transactional
    public ApiResponse<Void> startGame(int roomId, int userId) {
        // 1. 방 존재 여부 확인
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ApiResponse.invalidRequest(); // 존재하지 않는 방
        }

        // 2. 요청한 유저가 호스트인지 확인
        if (room.getHost() == null || room.getHost().getId() != userId) {
            return ApiResponse.invalidRequest(); // 호스트가 아님
        }

        // 3. 방 정원이 꽉 찼는지 확인 (현재 인원 수와 RoomType의 정원 비교)
        long currentUsers = userRoomRepository.countByRoom(room);
        if (currentUsers < room.getRoomType().getCapacity()) {
            return ApiResponse.invalidRequest(); // 정원이 다 차지 않음
        }

        // 4. 현재 방 상태가 WAIT인지 확인
        if (room.getStatus() != RoomStatus.WAIT) {
            return ApiResponse.invalidRequest(); // WAIT 상태가 아님
        }

        // 5. 방 상태를 PROGRESS로 변경
        room.setStatus(RoomStatus.PROGRESS);
        roomRepository.save(room);

        // 6. 1분 뒤 방 상태를 FINISH로 변경 (비동기 작업)
        try {
            scheduleFinishGame(room);
        } catch (RuntimeException e) {
            return ApiResponse.serverError(); // 서버 에러 응답 반환
        }

        return ApiResponse.success(null); // 성공 응답 반환
    }

}
