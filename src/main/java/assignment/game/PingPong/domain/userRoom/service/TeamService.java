package assignment.game.PingPong.domain.userRoom.service;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.room.entity.RoomStatus;
import assignment.game.PingPong.domain.room.repository.RoomRepository;
import assignment.game.PingPong.domain.userRoom.entity.Team;
import assignment.game.PingPong.domain.userRoom.entity.UserRoom;
import assignment.game.PingPong.domain.userRoom.repository.UserRoomRepository;
import assignment.game.PingPong.global.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    public TeamService(RoomRepository roomRepository, UserRoomRepository userRoomRepository) {
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
    }

    @Transactional
    public ApiResponse<Void> changeTeam(int roomId, int userId) {
        // 1. 방 존재 여부 확인
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ApiResponse.invalidRequest(); // 존재하지 않는 방
        }

        // 2. 유저가 해당 방에 참가 중인지 확인
        UserRoom userRoom = userRoomRepository.findByUserIdAndRoomId(userId, roomId);
        if (userRoom == null) {
            return ApiResponse.invalidRequest(); // 참가하지 않은 상태
        }

        // 3. 현재 방 상태가 WAIT인지 확인
        if (room.getStatus() != RoomStatus.WAIT) {
            return ApiResponse.invalidRequest(); // WAIT 상태가 아님
        }

        // 4. 현재 유저의 팀 확인 및 반대 팀 설정
        Team currentTeam = userRoom.getTeam();
        Team newTeam = (currentTeam == Team.RED) ? Team.BLUE : Team.RED;

        // 5. 변경되려는 팀의 인원 수 확인
        long newTeamCount = userRoomRepository.countByTeamAndRoom(newTeam, room);
        long maxCapacityPerTeam = room.getRoomType().getCapacity() / 2; // 정원의 절반

        if (newTeamCount >= maxCapacityPerTeam) {
            return ApiResponse.invalidRequest(); // 반대 팀이 가득 참
        }

        // 6. 팀 변경 처리
        userRoom.setTeam(newTeam);
        userRoomRepository.save(userRoom);

        return ApiResponse.success(null); // 성공 응답 반환
    }
}
