package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
