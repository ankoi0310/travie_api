package vn.edu.hcmuaf.fit.travie_api.core.shared.constants;

public class SecurityConstant {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "bearer";
    public static final String AUTHORITIES = "authorities";

    public static final String EXPIRED_TOKEN_MESSAGE = "Token hết hạn, vui lòng đăng nhập lại";
    public static final String INVALID_TOKEN_MESSAGE = "Token không hợp lệ";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token không thể xác thực";

    public static final String[] PUBLIC_URLS = {
            "/auth/login",
            "/auth/register",
            "/auth/verify",
            "/auth/forgot-password",
            "/auth/reset-password",
            "/auth/refresh-token",
            "/banner/**",
            "/hotel/**",
            "/room/**",
            "/review/**",
    };

    public static final String[] AUTHENTICATED_URLS = {
            "/user/**",
            "/invoice/**",
            "/booking/**",
    };
}
