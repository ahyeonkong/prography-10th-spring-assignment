package assignment.game.PingPong.domain.userRoom.repository;

import assignment.game.PingPong.domain.room.entity.Room;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.userRoom.entity.Team;
import assignment.game.PingPong.domain.userRoom.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    boolean existsByUser(User user); // 특정 유저가 이미 다른 방에 참여 중인지 확인

    int countByTeamAndRoom(Team team, Room room); // 특정 팀의 인원 수 확인

    int countByRoom(Room room); // 특정 방의 전체 인원 수 확인

    UserRoom findByUserAndRoom(User user, Room room); // 특정 User와 Room에 해당하는 UserRoom 조회

    void deleteByRoom(Room room); // 특정 Room에 해당하는 모든 UserRoom 삭제

}
