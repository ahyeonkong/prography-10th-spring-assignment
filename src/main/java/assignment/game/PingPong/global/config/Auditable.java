package assignment.game.PingPong.global.config;

import java.time.LocalDateTime;

/***
 * 나노초 제거 기능 구현
 */

public interface Auditable {

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);
}
