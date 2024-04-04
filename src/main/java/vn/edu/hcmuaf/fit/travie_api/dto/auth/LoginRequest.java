package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.annotation.EmailOrPhone;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @EmailOrPhone
    private String username;
    private String password;
}
