package vn.edu.hcmuaf.fit.travie_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.service.authentication.AuthenticationService;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest request) throws BaseException {
        RegisterResponse response = authenticationService.register(request);
        return ResponseEntity.ok(HttpResponse.success(response, "Đăng ký thành công!"));
    }

    @GetMapping("/verify")
    public ResponseEntity<HttpResponse> verify(@RequestParam UUID token) throws BaseException {
        authenticationService.verifyToken(token);
        return ResponseEntity.ok(HttpResponse.success("Xác thực thành công!"));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest request) throws BaseException {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(HttpResponse.success(response, "Đăng nhập thành công!"));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<HttpResponse> forgetPassword(@RequestParam("email") String email) throws BaseException {
        authenticationService.forgetPassword(email);
        return ResponseEntity.ok(HttpResponse.success("Gửi mail thành công. Vui lòng kiểm tra email của bạn!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) throws BaseException {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(HttpResponse.success("Đổi mật khẩu thành công!"));
    }
}
