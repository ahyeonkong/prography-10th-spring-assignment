package assignment.game.PingPong.global.init.controller;

import assignment.game.PingPong.global.init.dto.InitRequest;
import assignment.game.PingPong.global.init.service.InitService;
import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
public class InitController {

    private final InitService initService;

    @PostMapping
    public ApiResponse<Void> initializeData(@RequestBody InitRequest request) {
        return initService.initialize(request.getSeed(), request.getQuantity());
    }
}
