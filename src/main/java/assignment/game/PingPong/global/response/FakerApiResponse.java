package assignment.game.PingPong.global.response;

import assignment.game.PingPong.global.init.entity.FakerUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FakerApiResponse {
    private List<FakerUser> data; // fakerapi에서 반환되는 사용자 데이터 리스트
}
