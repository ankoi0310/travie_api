package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.constants.SecurityConstant;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String nickname;
    private String phone;
    private String avatar;
    private String accessToken;
    private String refreshToken;

    @Builder.Default
    private String tokenType = SecurityConstant.TOKEN_TYPE;
}
