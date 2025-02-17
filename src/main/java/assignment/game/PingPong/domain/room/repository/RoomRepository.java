package assignment.game.PingPong.domain.room.repository;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByHost(User host); // 특정 유저가 호스트로 설정된 방이 있는지 확인

}
