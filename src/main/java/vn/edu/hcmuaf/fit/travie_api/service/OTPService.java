package vn.edu.hcmuaf.fit.travie_api.service;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie_api.entity.OTP;

public interface OTPService {
    OTP generateOTP(String email, OTPType type) throws BaseException;

    OTP getOTPByEmail(String email) throws BaseException;

    OTP getOTPByCode(String code) throws BaseException;

    void verifyOTP(String code) throws BaseException;

    void deleteOTP() throws BaseException;
}
