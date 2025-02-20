package assignment.game.PingPong.domain.userRoom.entity;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UserRoom")
@Getter
@Setter
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id") // "room_id" 컬럼이 Room 엔티티의 ID를 참조
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // "user_id" 컬럼이 User 엔티티의 ID를 참조
    private User user;

    @Enumerated(EnumType.STRING)
    private Team team;

}
