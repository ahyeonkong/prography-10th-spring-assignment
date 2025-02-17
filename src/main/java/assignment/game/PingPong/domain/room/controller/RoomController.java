package assignment.game.PingPong.domain.room.controller;

import assignment.game.PingPong.domain.room.dto.CreateRoomRequest;
import assignment.game.PingPong.domain.room.dto.RoomResponse;
import assignment.game.PingPong.domain.room.service.RoomService;
import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ApiResponse<Void> createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request.getUserId(), request.getRoomType(), request.getTitle());
    }

    @GetMapping
    public ApiResponse<RoomResponse> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        RoomResponse result = roomService.getAllRooms(page, size);
        return ApiResponse.success(result);
    }
}

