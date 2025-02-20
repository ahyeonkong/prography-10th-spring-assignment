package assignment.game.PingPong.domain.room.entity;

import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.global.config.AuditEntityListener;
import assignment.game.PingPong.global.config.Auditable;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Room")
@Getter
@Setter
@EntityListeners(AuditEntityListener.class)
public class Room implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "host") // Room 테이블의 'host' 컬럼이 User 테이블의 ID를 참조, host는 방을 생성한 사람
    private User host;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
