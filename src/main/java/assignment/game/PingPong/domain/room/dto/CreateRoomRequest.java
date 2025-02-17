package assignment.game.PingPong.domain.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequest {
    private int userId;   // 유저 ID
    private String roomType; // 방 타입
    private String title;   // 방 제목
}