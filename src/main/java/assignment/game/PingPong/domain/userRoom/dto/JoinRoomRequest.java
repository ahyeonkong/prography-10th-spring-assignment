package assignment.game.PingPong.domain.userRoom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRoomRequest {
    private int userId; // 참가하려는 유저 ID
}
