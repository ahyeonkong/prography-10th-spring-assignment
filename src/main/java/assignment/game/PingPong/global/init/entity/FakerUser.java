package assignment.game.PingPong.global.init.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakerUser {
    private int id;           // fakerId로 매핑됨
    private String username;  // name으로 매핑됨
    private String email;     // 그대로 사용됨

    // uuid, firstname, lastname, password, ip, macAddress, website, image 필드는 사용하지 않음
}
