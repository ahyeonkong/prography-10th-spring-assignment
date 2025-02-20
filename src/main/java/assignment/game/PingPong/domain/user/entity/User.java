package assignment.game.PingPong.domain.user.entity;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.global.config.AuditEntityListener;
import assignment.game.PingPong.global.config.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"User\"")
@Getter
@Setter
@EntityListeners(AuditEntityListener.class)
public class User implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "host", fetch = FetchType.LAZY) // Room 엔티티가 관계의 소유자임을 명시
    private Room room;
}
