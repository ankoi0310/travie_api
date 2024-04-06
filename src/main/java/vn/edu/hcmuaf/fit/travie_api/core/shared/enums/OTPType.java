package vn.edu.hcmuaf.fit.travie_api.core.shared.enums;

import lombok.Getter;

@Getter
public enum OTPType {
    VERIFICATION(5 * 60),
    RESET_PASSWORD(5 * 60),
    TWO_FACTOR_AUTHENTICATION(30);

    OTPType(int expireTime) {
        this.expireTime = expireTime;
    }

    private final int expireTime; // in seconds
}
