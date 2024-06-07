package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.*;
import vn.edu.hcmuaf.fit.travie_api.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<HttpResponse> search(HotelSearch hotelSearch) {
        List<HotelDTO> hotels = hotelService.search(hotelSearch);
        return ResponseEntity.ok(HttpResponse.success(hotels, "Tìm kiếm thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getHotelById(@PathVariable long id) throws BaseException {
        HotelDTO hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(HttpResponse.success(hotel, "Lấy thông tin khách sạn thành công"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createHotel(@RequestBody HotelCreate hotelCreate) throws BaseException {
        HotelDTO hotel = hotelService.createHotel(hotelCreate);
        return ResponseEntity.ok(HttpResponse.success(hotel, "Tạo khách sạn thành công"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateHotel(@PathVariable long id, @RequestBody HotelCreate hotelCreate) throws BaseException {
        HotelDTO hotel = hotelService.updateHotel(id, hotelCreate);
        return ResponseEntity.ok(HttpResponse.success(hotel, "Cập nhật khách sạn thành công"));
    }
}
