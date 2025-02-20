package assignment.game.PingPong.domain.room.controller;

import assignment.game.PingPong.domain.room.dto.CreateRoomRequest;
import assignment.game.PingPong.domain.room.dto.StartGameRequest;
import assignment.game.PingPong.domain.userRoom.dto.JoinRoomRequest;
import assignment.game.PingPong.domain.room.dto.RoomDetailResponse;
import assignment.game.PingPong.domain.room.dto.RoomResponse;
import assignment.game.PingPong.domain.room.service.RoomService;
import assignment.game.PingPong.domain.userRoom.dto.LeaveRoomRequest;
import assignment.game.PingPong.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    // 방 생성 API
    @PostMapping
    public ApiResponse<Void> createRoom(@RequestBody CreateRoomRequest request) {
        return roomService.createRoom(request.getUserId(), request.getRoomType(), request.getTitle());
    }

    // 방 전체 조회 API
    @GetMapping
    public ApiResponse<RoomResponse> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        RoomResponse result = roomService.getAllRooms(page, size).getResult();
        return ApiResponse.success(result);
    }

    // 방 상세 조회 API
    @GetMapping("/{roomId}")
    public ApiResponse<RoomDetailResponse> getRoomDetail(@PathVariable int roomId) {
        return roomService.getRoomDetail(roomId);
    }

    // 방 참가 API
    @PostMapping("/attention/{roomId}")
    public ApiResponse<Void> joinRoom(@PathVariable int roomId, @RequestBody JoinRoomRequest request) {
        return roomService.joinRoom(roomId, request.getUserId());
    }

    // 방 나가기 API
    @PostMapping("/out/{roomId}")
    public ApiResponse<Void> leaveRoom(@PathVariable int roomId, @RequestBody LeaveRoomRequest request) {
        return roomService.leaveRoom(roomId, request.getUserId());
    }

    // 게임 시작 API
    @PutMapping("/start/{roomId}")
    public ApiResponse<Void> startGame(@PathVariable int roomId, @RequestBody StartGameRequest request) {
        return roomService.startGame(roomId, request.getUserId());
    }
}
