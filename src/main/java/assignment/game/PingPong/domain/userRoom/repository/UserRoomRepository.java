package assignment.game.PingPong.domain.userRoom.repository;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.userRoom.entity.Team;
import assignment.game.PingPong.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {

    boolean existsByUser(User user); // 특정 유저가 이미 다른 방에 참여 중인지 확인

    long countByTeamAndRoom(Team team, Room room); // 특정 팀의 인원 수 확인

    long countByRoom(Room room); // 특정 방의 전체 인원 수 확인
}
