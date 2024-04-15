package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelCreate;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.HotelDTO;
import vn.edu.hcmuaf.fit.travie_api.service.hotel.HotelService;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<HttpResponse> getHotels() {
        List<HotelDTO> hotels = hotelService.getHotels();
        return ResponseEntity.ok(HttpResponse.success(hotels, "Get hotels successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getHotelById(@PathVariable Long id) throws BaseException {
        HotelDTO hotelDTO = hotelService.getHotelById(id);
        return ResponseEntity.ok(HttpResponse.success(hotelDTO, "Get hotel by id successfully!"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createHotel(@RequestBody HotelCreate hotelCreate) throws BaseException {
        HotelDTO hotelDTO = hotelService.createHotel(hotelCreate);
        return ResponseEntity.ok(HttpResponse.success(hotelDTO, "Hotel created successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateHotel(@PathVariable Long id, @RequestBody HotelCreate hotelCreate) throws BaseException {
        HotelDTO hotelDTO = hotelService.updateHotel(id, hotelCreate);
        return ResponseEntity.ok(HttpResponse.success(hotelDTO, "Hotel updated successfully!"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<HttpResponse> updateHotelStatus(@PathVariable Long id, @RequestParam boolean status) throws BaseException {
        hotelService.updateHotelStatus(id, status);
        return ResponseEntity.ok(HttpResponse.success(null, "Hotel status updated successfully!"));
    }
}
