package vn.edu.hcmuaf.fit.travie_api.dto.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private String fullName;
    private String email;
}
