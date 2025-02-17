package assignment.game.PingPong.global.config;

import assignment.game.PingPong.domain.user.entity.Status;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        // 테스트 데이터 생성
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setFakerId(1000 + i);
            user.setName("User" + i);
            user.setEmail("user" + i + "@example.com");
            user.setStatus(i % 2 == 0 ? Status.ACTIVE : Status.WAIT); // 짝수는 ACTIVE, 홀수는 WAIT
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }

}
