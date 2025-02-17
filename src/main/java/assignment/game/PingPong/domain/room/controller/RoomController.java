package assignment.game.PingPong.domain.room.controller;

import assignment.game.PingPong.domain.room.dto.CreateRoomRequest;
import assignment.game.PingPong.domain.room.dto.RoomDetailResponse;
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

    // 방 생성
    @PostMapping
    public ApiResponse<Void> createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request.getUserId(), request.getRoomType(), request.getTitle());
    }

    // 방 전체 조회
    @GetMapping
    public ApiResponse<RoomResponse> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        RoomResponse result = roomService.getAllRooms(page, size);
        return ApiResponse.success(result);
    }

    // 방 상세 조회
    @GetMapping("/{roomId}")
    public ApiResponse<RoomDetailResponse> getRoomDetail(@PathVariable int roomId) {
        return roomService.getRoomDetail(roomId);
    }
}

