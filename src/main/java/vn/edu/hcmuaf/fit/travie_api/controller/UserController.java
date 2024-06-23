package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.ChangePasswordRequest;
import vn.edu.hcmuaf.fit.travie_api.dto.invoice.InvoiceDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileDTO;
import vn.edu.hcmuaf.fit.travie_api.dto.user.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie_api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<HttpResponse> getProfile() throws BaseException {
        UserProfileDTO userProfile = userService.getProfile();
        return ResponseEntity.ok(HttpResponse.success(userProfile, "Lấy thông tin thành công!"));
    }

    @PutMapping("/profile")
    public ResponseEntity<HttpResponse> updateProfile(@RequestBody UserProfileRequest userProfileRequest) throws BaseException {
        UserProfileDTO userProfile = userService.updateProfile(userProfileRequest);
        return ResponseEntity.ok(HttpResponse.success(userProfile, "Cập nhật thông tin thành công!"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<HttpResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws BaseException {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(HttpResponse.success("Đổi mật khẩu thành công!"));
    }

    @GetMapping("/booking-history")
    public ResponseEntity<HttpResponse> getBookingHistory() throws BaseException {
        List<InvoiceDTO> invoices = userService.getBookingHistory();
        return ResponseEntity.ok(HttpResponse.success(invoices, "Lấy lịch sử đặt vé thành công!"));
    }
}
