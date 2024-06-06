package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @NotBlank
    String email;
}
