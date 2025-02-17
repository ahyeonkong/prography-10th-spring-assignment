package assignment.game.PingPong.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDetailResponse {
    private int id;          // 방 ID
    private String title;    // 방 제목
    private int hostId;      // 호스트 ID
    private String roomType; // SINGLE, DOUBLE
    private String status;   // WAIT, PROGRESS, FINISH
    private String createdAt; // yyyy-MM-dd HH:mm:ss 형식의 생성 시간
    private String updatedAt; // yyyy-MM-dd HH:mm:ss 형식의 수정 시간
}
