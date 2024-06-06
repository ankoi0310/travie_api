package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;
import vn.edu.hcmuaf.fit.travie_api.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @RequestMapping
    public ResponseEntity<List<RoomDTO>> getRooms(@RequestBody RoomSearch roomSearch) {
        List<RoomDTO> rooms = roomService.search(roomSearch);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable long id) throws BaseException {
        RoomDTO roomDTO = roomService.getRoomById(id);
        return ResponseEntity.ok(roomDTO);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomCreate roomCreate) throws BaseException {
        RoomDTO roomDTO = roomService.createRoom(roomCreate);
        return ResponseEntity.ok(roomDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable long id, @RequestBody RoomUpdate roomUpdate) throws BaseException {
        RoomDTO roomDTO = roomService.updateRoom(id, roomUpdate);
        return ResponseEntity.ok(roomDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable long id) throws BaseException {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
