package assignment.game.PingPong.global.health;

import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthCheckController {

    // 헬스체크 API 호출 여부를 추적하는 상태 변수
    private final AtomicBoolean isHealthCheckCalled = new AtomicBoolean(false);

    // 헬스체크 API
    @GetMapping
    public ApiResponse<Void> healthCheck() {
        // 최초 호출 여부 확인
        if (isHealthCheckCalled.compareAndSet(false, true)) {
            // 최초 호출: 상태를 true로 설정하고 성공 응답 반환
            return ApiResponse.success(null);
        } else {
            // 이미 호출된 경우
            return ApiResponse.serverError();
        }
    }
}
