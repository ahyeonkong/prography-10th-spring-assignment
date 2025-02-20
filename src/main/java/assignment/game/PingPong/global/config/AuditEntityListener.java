package assignment.game.PingPong.global.config;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable) {
            Auditable auditable = (Auditable) entity;
            LocalDateTime now = DateTimeUtils.removeNano(LocalDateTime.now());
            auditable.setCreatedAt(now);
            auditable.setUpdatedAt(now);
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Auditable) {
            Auditable auditable = (Auditable) entity;
            LocalDateTime now = DateTimeUtils.removeNano(LocalDateTime.now());
            auditable.setUpdatedAt(now);
        }
    }
}
