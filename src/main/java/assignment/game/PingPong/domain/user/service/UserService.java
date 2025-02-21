package assignment.game.PingPong.domain.user.service;

import assignment.game.PingPong.domain.user.dto.UserResponse;
import assignment.game.PingPong.domain.user.entity.User;
import assignment.game.PingPong.domain.user.repository.UserRepository;
import assignment.game.PingPong.global.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    
    // 유저 전체 조회
    public ApiResponse<UserResponse> getAllUsers(int page, int size) {
        // 유효성 검사
        if (page < 0 || size < 1) {
            return ApiResponse.invalidRequest();
        }

        // 페이징 및 정렬 설정
        // id 기준 오름차순으로 정렬해서 반환
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> userPage = userRepository.findAll(pageRequest);

        // 결과 매핑 및 반환
        List<UserResponse.UserDetail> userList = userPage.getContent().stream().map(user -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return new UserResponse.UserDetail(
                    user.getId(),
                    user.getFakerId(),
                    user.getName(),
                    user.getEmail(),
                    user.getStatus().name(),
                    user.getCreatedAt().format(formatter),
                    user.getUpdatedAt().format(formatter)
            );
        }).collect(Collectors.toList());

        UserResponse response = new UserResponse((int) userPage.getTotalElements(), userPage.getTotalPages(), userList);
        return ApiResponse.success(response);
    }

}
