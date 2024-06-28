package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.room.*;
import vn.edu.hcmuaf.fit.travie_api.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> search(RoomSearch roomSearch) {
        List<RoomDTO> rooms = roomService.search(roomSearch);
        return ResponseEntity.ok(HttpResponse.success(rooms, "Tìm kiếm phòng thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getRoomById(@PathVariable long id) throws BaseException {
        RoomDTO roomDTO = roomService.getRoomById(id);
        return ResponseEntity.ok(HttpResponse.success(roomDTO, "Lấy thông tin phòng thành công"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createRoom(@RequestBody RoomCreate roomCreate) throws BaseException {
        RoomDTO roomDTO = roomService.createRoom(roomCreate);
        return ResponseEntity.ok(HttpResponse.success(roomDTO, "Tạo phòng thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateRoom(@PathVariable long id, @RequestBody RoomUpdate roomUpdate) throws BaseException {
        RoomDTO roomDTO = roomService.updateRoom(id, roomUpdate);
        return ResponseEntity.ok(HttpResponse.success(roomDTO, "Cập nhật phòng thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteRoom(@PathVariable long id) throws BaseException {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(HttpResponse.success(null, "Xóa phòng thành công"));
    }
}
