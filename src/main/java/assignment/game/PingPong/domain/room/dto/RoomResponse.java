package assignment.game.PingPong.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomResponse {
    private int totalElements; // 전체 방 수
    private int totalPages;     // 전체 페이지 수
    private List<RoomDetail> roomList; // 방 목록

    @Data
    @AllArgsConstructor
    public static class RoomDetail {
        private int id;          // 방 ID
        private String title;    // 방 제목
        private int hostId;      // 호스트 ID
        private String roomType; // SINGLE, DOUBLE
        private String status;   // WAIT, PROGRESS, FINISH
    }
}
