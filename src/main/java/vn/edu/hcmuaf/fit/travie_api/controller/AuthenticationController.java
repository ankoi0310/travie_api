package vn.edu.hcmuaf.fit.travie_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.ServiceUnavailableException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;
import vn.edu.hcmuaf.fit.travie_api.dto.auth.*;
import vn.edu.hcmuaf.fit.travie_api.dto.user.AppUserDTO;
import vn.edu.hcmuaf.fit.travie_api.service.authentication.AuthenticationService;
import vn.edu.hcmuaf.fit.travie_api.service.otp.OtpService;
import vn.edu.hcmuaf.fit.travie_api.service.user.UserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest registerRequest) throws BaseException {
        try {
            RegisterResponse registerResponse = authenticationService.register(registerRequest);

            // Generate OTP
            String otp = otpService.generateOTP(registerResponse.getEmail(), OTPType.VERIFICATION);

            // Send email verification
            CompletableFuture<Boolean> emailSendingFuture = otpService.sendOTP(
                    registerResponse.getFullName(),
                    registerResponse.getEmail(),
                    otp,
                    OTPType.VERIFICATION
            );

            boolean isSent = emailSendingFuture.get();

            if (!isSent) {
                throw new ServiceUnavailableException("Gửi email xác thực thất bại");
            }

            return ResponseEntity.ok(HttpResponse.success(registerResponse, "Đăng ký thành công!"));
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể gửi email xác thực");
        }
    }

    @GetMapping("/register/resend-otp")
    public ResponseEntity<HttpResponse> resendOtpRegister(@RequestParam String email) throws BaseException {
//        authenticationService.verify(otp);
        return ResponseEntity.ok(HttpResponse.success("Xác thực thành công!"));
    }

    @GetMapping("/verify")
    public ResponseEntity<HttpResponse> verify(@RequestParam String otp) throws BaseException {
        authenticationService.verify(otp);
        return ResponseEntity.ok(HttpResponse.success("Xác thực thành công!"));
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody LoginRequest request) throws BaseException {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(HttpResponse.success(response, "Đăng nhập thành công!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<HttpResponse> forgotPassword(@RequestParam("email") String email) throws BaseException {
        try {
            AppUserDTO appUserDTO = userService.getUserByEmail(email);

            // Generate OTP
            String otp = otpService.generateOTP(appUserDTO.getEmail(), OTPType.RESET_PASSWORD);

            // Send email verification
            CompletableFuture<Boolean> emailSendingFuture = otpService.sendOTP(
                    appUserDTO.getUserInfoDTO().getFullName(),
                    appUserDTO.getEmail(),
                    otp,
                    OTPType.RESET_PASSWORD
            );

            boolean isSent = emailSendingFuture.get();

            if (!isSent) {
                throw new ServiceUnavailableException("Gửi email đặt lại mật khẩu thất bại");
            }

            return ResponseEntity.ok(HttpResponse.success("Gửi mail thành công. Vui lòng kiểm tra email của bạn!"));
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.toString());
            throw new ServiceUnavailableException("Không thể gửi email đặt lại mật khẩu");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HttpResponse> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) throws BaseException {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(HttpResponse.success("Đổi mật khẩu thành công!"));
    }
}
