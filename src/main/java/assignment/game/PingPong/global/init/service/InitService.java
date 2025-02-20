package assignment.game.PingPong.global.init.service;

import assignment.game.PingPong.domain.user.entity.Status;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.user.repository.UserRepository;
import assignment.game.PingPong.global.init.entity.FakerUser;
import assignment.game.PingPong.global.response.ApiResponse;
import assignment.game.PingPong.global.response.FakerApiResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public InitService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public ApiResponse<Void> initialize(int seed, int quantity) {
        // 1. 데이터베이스 초기화
        resetDatabase();

        // 2. fakerapi 호출
        String fakerApiUrl = String.format("https://fakerapi.it/api/v1/users?_seed=%d&_quantity=%d&_locale=ko_KR", seed, quantity);
        FakerApiResponse response;

        try {
            response = restTemplate.getForObject(fakerApiUrl, FakerApiResponse.class);
        } catch (Exception e) {
            return ApiResponse.serverError();
        }

        if (response.getData() == null) {
            return ApiResponse.serverError();
        }

        List<FakerUser> fakerUsers = response.getData();

        // 3. 데이터 변환 및 저장
        List<User> users = fakerUsers.stream()
                .sorted(Comparator.comparingInt(FakerUser::getId)) // id 오름차순 정렬
                .map(this::mapToUserEntity)
                .collect(Collectors.toList());

        userRepository.saveAll(users);

        return ApiResponse.success(null); // 성공 응답 반환
    }

    @Transactional
    public void resetDatabase() {
        // 테이블 이름 리스트 (순서 중요: 자식 테이블 → 부모 테이블)
        List<String> tables = List.of("UserRoom", "Room", "User");

        // 1. 모든 데이터 삭제
        tables.forEach(table ->
                entityManager.createNativeQuery("DELETE FROM " + table).executeUpdate()
        );

        // AUTO_INCREMENT 초기화는 하지 않음 (id는 계속 증가)
    }

    private User mapToUserEntity(FakerUser fakerUser) {
        User user = new User();
        user.setFakerId(fakerUser.getId()); // id -> fakerId로 저장
        user.setName(fakerUser.getUsername()); // username -> name으로 저장
        user.setEmail(fakerUser.getEmail()); // email 그대로 저장

        // 회원 상태 설정
        if (fakerUser.getId() <= 30) {
            user.setStatus(Status.ACTIVE); // 활성 상태
        } else if (fakerUser.getId() <= 60) {
            user.setStatus(Status.WAIT); // 대기 상태
        } else {
            user.setStatus(Status.NON_ACTIVE); // 비활성 상태
        }

        LocalDateTime now = LocalDateTime.now().withNano(0);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }
}
