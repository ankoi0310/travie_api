package vn.edu.hcmuaf.fit.travie_api.core.shared.constants;

import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.user.UserRole;

public class AppConstant {
    public static final UserRole DEFAULT_ROLE = UserRole.CUSTOMER;

    public static final long VERIFICATION_TOKEN_EXPIRED_DATE = 1; // 1 day
    public static final long RESET_PASSWORD_TOKEN_EXPIRED_DATE = 5; // 5 minutes
}
