package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String email;
}
