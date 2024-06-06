package vn.edu.hcmuaf.fit.travie_api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPState;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie_api.entity.OTP;
import vn.edu.hcmuaf.fit.travie_api.repository.OTPRepository;
import vn.edu.hcmuaf.fit.travie_api.service.OTPService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
    private final OTPRepository otpRepository;

    @Value("${otp.length}")
    private int length;

    @Value("${otp.expired-time}")
    private int expiredTime;

    @Override
    public OTP generateOTP(String email, OTPType type) throws BaseException {
        try {
            // Check if already exist OTP with email
            OTP otpExist = otpRepository.findByEmail(email).orElse(null);

            // if otp existed, check limit resend otp time
            if (otpExist != null) {
                // create new code and update resend count
                String code = RandomStringUtils.randomNumeric(length);
                otpExist.setCode(code);
                otpRepository.save(otpExist);

                return otpExist;
            }

            String code = RandomStringUtils.randomNumeric(length);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiredAt = now.plusSeconds(expiredTime);

            OTP otp = OTP.builder()
                         .code(code)
                         .email(email)
                         .type(type)
                         .state(OTPState.ACTIVE)
                         .sentAt(now)
                         .expiredAt(expiredAt)
                         .build();

            otpRepository.save(otp);

            return otp;
        } catch (Exception e) {
            log.error("Error generate OTP: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể tạo mã OTP");
        }
    }

    @Override
    public OTP getOTPByEmail(String email) throws BaseException {
        try {
            return otpRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Không tìm thấy OTP"));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error get OTP by email: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể lấy mã OTP");
        }
    }

    @Override
    public OTP getOTPByCode(String code) throws BaseException {
        try {
            return otpRepository.findByCode(code).orElseThrow(() -> new NotFoundException("Không tìm thấy OTP"));
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error get OTP by code: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể lấy mã OTP");
        }
    }

    @Override
    public void verifyOTP(String code) throws BaseException {
        OTP otp = otpRepository.findByCode(code).orElseThrow(() -> new UnauthorizedException("OTP không hợp lệ"));

        if (otp.getState() == OTPState.EXPIRED) {
            throw new GoneException("Mã OTP đã hết hạn");
        }

        if (otp.getState() == OTPState.VERIFIED) {
            throw new GoneException("Mã OTP đã được xác thực");
        }

        otp.setState(OTPState.VERIFIED);
        otpRepository.save(otp);
    }

    @Override
    public void deleteOTP() throws BaseException {
        try {
            otpRepository.delete();
        } catch (Exception e) {
            log.error("Error delete OTP: {}", e.getMessage());
            throw new ServiceUnavailableException("Không thể xóa mã OTP");
        }
    }
}
