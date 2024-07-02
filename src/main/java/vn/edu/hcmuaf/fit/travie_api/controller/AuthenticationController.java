package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/check-email")
    public ResponseEntity<HttpResponse> checkExistEmail(@RequestParam String email) throws BaseException {
        authenticationService.checkExistEmail(email);
        return ResponseEntity.ok(HttpResponse.success("Email hợp lệ!"));
    }

    @PostMapping(path = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<HttpResponse> register(@ModelAttribute RegisterRequest request) throws BaseException {
        RegisterResponse registerResponse = authenticationService.register(request);
        return ResponseEntity.ok(HttpResponse.success(registerResponse, "Vui lòng kiểm tra email để lấy mã OTP!"));
    }

    @PostMapping("/verify")
    public ResponseEntity<HttpResponse> verify(@RequestParam String code) throws BaseException {
        String message = authenticationService.verify(code);
        return ResponseEntity.ok(HttpResponse.success(message));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse, "Đăng nhập thành công!"));
    }

    @PostMapping("/login/facebook")
    public ResponseEntity<HttpResponse> loginFacebook(@RequestParam String accessToken) throws Exception {
        LoginResponse loginResponse = authenticationService.loginFacebook(accessToken);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse, "Đăng nhập thành công!"));
    }

    @PostMapping("/login/google")
    public ResponseEntity<HttpResponse> loginGoogle(@RequestParam String accessToken) throws Exception {
        LoginResponse loginResponse = authenticationService.loginGoogle(accessToken);
        return ResponseEntity.ok().body(HttpResponse.success(loginResponse, "Đăng nhập thành công!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<HttpResponse> forgotPassword(@RequestParam String email) throws BaseException {
        authenticationService.forgotPassword(email);
        return ResponseEntity.ok(HttpResponse.success("Vui lòng kiểm tra email để lấy mã OTP!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordRequest request) throws BaseException {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(HttpResponse.success("Đặt lại mật khẩu thành công!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<HttpResponse> refreshToken(@RequestParam String token) throws BaseException {
        RefreshTokenResponse refreshTokenResponse = authenticationService.refreshToken(token);
        return ResponseEntity.ok(HttpResponse.success(refreshTokenResponse, "Làm mới token thành công!"));
    }
}
