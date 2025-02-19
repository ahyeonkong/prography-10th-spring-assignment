package assignment.game.PingPong.domain.userRoom.controller;

import assignment.game.PingPong.domain.userRoom.dto.ChangeTeamRequest;
import assignment.game.PingPong.domain.userRoom.service.TeamService;
import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    @PutMapping("/{roomId}")
    public ApiResponse<Void> changeTeam(@PathVariable int roomId, @RequestBody ChangeTeamRequest request) {
        return teamService.changeTeam(roomId, request.getUserId());
    }
}
