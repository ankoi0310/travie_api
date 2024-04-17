package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.service.booking.BookingService;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<HttpResponse> searchHistory() {
        return null;
    }

    @PostMapping
    public ResponseEntity<HttpResponse> book(@RequestBody BookingRequest bookingRequest) throws BaseException {
        BookingDTO bookingDTO = bookingService.bookRoom(bookingRequest);
        return ResponseEntity.ok(HttpResponse.success(bookingDTO, "Booking successful"));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<HttpResponse> confirm(@PathVariable Long id) throws BaseException {
        return null;
    }

    @PutMapping("/{id}/check-in")
    public ResponseEntity<HttpResponse> checkIn(@PathVariable Long id) throws BaseException {
        return null;
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<HttpResponse> complete(@PathVariable Long id) throws BaseException {
        return null;
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<HttpResponse> cancel(@PathVariable Long id) throws BaseException {
        return null;
    }
}
