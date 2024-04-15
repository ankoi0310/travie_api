package vn.edu.hcmuaf.fit.travie_api.core.shared.constants;

public class SecurityConstant {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "authorities";

    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

    public static final String[] PUBLIC_URLS = {
            "/**",
    };

    public static final String[] AUTHENTICATED_URLS = {
            "/room/**",
    };
}
