package assignment.game.PingPong.domain.room.controller;

import assignment.game.PingPong.domain.room.dto.CreateRoomRequest;
import assignment.game.PingPong.domain.room.service.RoomService;
import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/room")
    public ApiResponse<Void> createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request.getUserId(), request.getRoomType(), request.getTitle());
    }
}
