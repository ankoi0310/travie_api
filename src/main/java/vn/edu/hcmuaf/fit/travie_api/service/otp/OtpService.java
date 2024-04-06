package vn.edu.hcmuaf.fit.travie_api.service.otp;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;

import java.util.concurrent.CompletableFuture;

public interface OtpService {
    String generateOTP(String email, OTPType type) throws BaseException;

    CompletableFuture<Boolean> sendOTP(String name, String email, String otp, OTPType type) throws BaseException;

    void validateOTP(String otp) throws BaseException;
}
