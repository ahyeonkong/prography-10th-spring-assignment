package assignment.game.PingPong.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private int totalElements;
    private int totalPages;
    private List<UserDetail> userList;

    @Data
    @AllArgsConstructor
    public static class UserDetail {
        private int id;
        private int fakerId;
        private String name;
        private String email;
        private String status;
        private String createdAt; // yyyy-MM-dd HH:mm:ss
        private String updatedAt; // yyyy-MM-dd HH:mm:ss
    }

}