package assignment.game.PingPong.domain.user.entity;

import assignment.game.PingPong.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"User\"")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int fakerId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "host", fetch = FetchType.LAZY) // Room 엔티티가 관계의 소유자임을 명시
    private Room room;
}
