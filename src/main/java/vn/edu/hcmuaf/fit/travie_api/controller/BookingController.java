package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.booking.BookingRequest;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.payment.payos.LinkCreationResponse;
import vn.edu.hcmuaf.fit.travie_api.service.BookingService;

import java.io.IOException;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/cancel-checkout")
    public ResponseEntity<HttpResponse> cancelCheckout(@RequestParam int code) throws BaseException {
        InvoiceDTO invoice = bookingService.cancelCheckout(code);
        return ResponseEntity.ok(HttpResponse.success(invoice, "Hủy phòng thành công!"));
    }

    @GetMapping("/complete-checkout")
    public ResponseEntity<HttpResponse> completeCheckout(@RequestParam int code) throws BaseException {
        InvoiceDTO invoice = bookingService.completeCheckout(code);
        return ResponseEntity.ok(HttpResponse.success(invoice, "Thanh toán thành công!"));
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createBooking(@RequestBody BookingRequest request) throws BaseException,
            IOException {
        LinkCreationResponse linkCreationResponse = bookingService.createBooking(request);
        return ResponseEntity.ok(HttpResponse.success(linkCreationResponse, "Đặt phòng thành công!"));
    }
}
