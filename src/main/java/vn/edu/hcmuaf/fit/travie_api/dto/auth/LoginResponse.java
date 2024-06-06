package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.Builder;
import lombok.Data;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.SecurityConstant;

@Data
@Builder
public class LoginResponse {
    private String welcomeName;
    private String accessToken;
    private String refreshToken;

    @Builder.Default
    private String tokenType = SecurityConstant.TOKEN_TYPE;
}
