package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ResetPasswordRequest {
    @NotNull(message = "Token không hợp lệ!")
    private UUID token;

    private String password;
}
