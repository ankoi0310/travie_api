package vn.edu.hcmuaf.fit.travie_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.service.otp.OtpService;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @GetMapping("/validate")
    public void validateOtp(@RequestParam String otp) throws BaseException {
        otpService.validateOTP(otp);
    }
}
