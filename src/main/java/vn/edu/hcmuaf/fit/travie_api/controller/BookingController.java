package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.BookingStatus;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.service.BookingService;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

}
