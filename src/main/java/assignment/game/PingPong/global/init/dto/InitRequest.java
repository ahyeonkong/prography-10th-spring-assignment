package assignment.game.PingPong.global.init.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitRequest {
    private int seed;      // fakerapi의 seed 값
    private int quantity;  // 생성할 데이터 수량
}
