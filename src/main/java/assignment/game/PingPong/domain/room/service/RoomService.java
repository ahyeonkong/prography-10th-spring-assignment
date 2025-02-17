package assignment.game.PingPong.domain.room.service;

import assignment.game.PingPong.domain.room.dto.RoomResponse;
import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.room.entity.RoomType;
import assignment.game.PingPong.domain.room.repository.RoomRepository;
import assignment.game.PingPong.domain.room.entity.RoomStatus;
import assignment.game.PingPong.domain.user.entity.Status;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.user.repository.UserRepository;
import assignment.game.PingPong.global.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
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

        roomRepository.save(room);

        return ApiResponse.success(null); // 성공 응답 반환
    }

    public RoomResponse getAllRooms(int page, int size) {
        // 페이징 및 정렬 설정 (id 기준 오름차순)
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Room> roomPage = roomRepository.findAll(pageRequest);

        List<RoomResponse.RoomDetail> roomList = roomPage.getContent().stream().map(room ->
                new RoomResponse.RoomDetail(
                        room.getId(),
                        room.getTitle(),
                        room.getHost().getId(),
                        room.getRoomType().toString(),
                        room.getStatus().name()
                )
        ).collect(Collectors.toList());

        return new RoomResponse((int) roomPage.getTotalElements(), roomPage.getTotalPages(), roomList);
    }
}

