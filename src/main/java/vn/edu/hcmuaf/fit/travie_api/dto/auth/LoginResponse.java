package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String fullName;
    private String email;
    private String accessToken;
}
