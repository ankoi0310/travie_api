package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;
import vn.edu.hcmuaf.fit.travie_api.service.room.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @RequestMapping
    public ResponseEntity<HttpResponse> getRooms(@RequestBody RoomSearch roomSearch) {
        List<RoomDTO> rooms = roomService.search(roomSearch);
        return ResponseEntity.ok(HttpResponse.success(rooms, "Get rooms successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getRoomById(@PathVariable Long id) throws BaseException {
        RoomDTO roomDTO = roomService.getRoomById(id);
        return ResponseEntity.ok(HttpResponse.success(roomDTO, "Get room by id successfully!"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createRoom(@RequestBody RoomCreate roomCreate) throws BaseException {
        RoomDTO roomDTO = roomService.createRoom(roomCreate);
        return ResponseEntity.ok(HttpResponse.success(roomDTO, "Room created successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateRoom(@PathVariable Long id, @RequestBody RoomUpdate roomUpdate) throws BaseException {
        RoomDTO roomDTO = roomService.updateRoom(id, roomUpdate);
        return ResponseEntity.ok(HttpResponse.success(roomDTO, "Room updated successfully!"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<HttpResponse> updateRoomStatus(@PathVariable Long id, @RequestParam boolean status) throws BaseException {
        roomService.updateRoomStatus(id, status);
        return ResponseEntity.ok(HttpResponse.success(null, "Room status updated successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteRoom(@PathVariable Long id) throws BaseException {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(HttpResponse.success(null, "Room deleted successfully!"));
    }
}
