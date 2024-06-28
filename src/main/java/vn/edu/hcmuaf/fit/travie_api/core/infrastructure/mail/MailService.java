package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.mail;

import vn.edu.hcmuaf.fit.travie_api.core.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPType;

public interface MailService {

    void sendOTP(String email, String code, OTPType type) throws BaseException;

}
