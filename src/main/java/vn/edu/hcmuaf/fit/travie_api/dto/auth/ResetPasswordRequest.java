package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotNull(message = "Token không hợp lệ!")
    private String otp;

    private String password;
}
