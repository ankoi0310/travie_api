package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.hotel.*;
import vn.edu.hcmuaf.fit.travie_api.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> search(HotelSearch hotelSearch) {
        List<HotelDTO> hotels = hotelService.search(hotelSearch);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable long id) throws BaseException {
        HotelDTO hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelCreate hotelCreate) throws BaseException {
        HotelDTO hotel = hotelService.createHotel(hotelCreate);
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable long id, @RequestBody HotelCreate hotelCreate) throws BaseException {
        HotelDTO hotel = hotelService.updateHotel(id, hotelCreate);
        return ResponseEntity.ok(hotel);
    }
}
