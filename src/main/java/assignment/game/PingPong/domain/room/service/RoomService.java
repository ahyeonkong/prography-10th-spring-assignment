package assignment.game.PingPong.domain.room.service;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.room.entity.RoomType;
import assignment.game.PingPong.domain.room.repository.RoomRepository;
import assignment.game.PingPong.domain.room.entity.RoomStatus;
import assignment.game.PingPong.domain.user.entity.Status;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.user.repository.UserRepository;
import assignment.game.PingPong.global.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
