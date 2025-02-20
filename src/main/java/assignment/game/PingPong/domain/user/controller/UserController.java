package assignment.game.PingPong.domain.user.controller;

import assignment.game.PingPong.domain.user.dto.UserResponse;
import assignment.game.PingPong.domain.user.service.UserService;
import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ApiResponse<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        UserResponse result = userService.getAllUsers(page, size).getResult();
        return ApiResponse.success(result);
    }

}
