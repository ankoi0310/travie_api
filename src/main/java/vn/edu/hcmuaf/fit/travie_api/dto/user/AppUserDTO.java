package vn.edu.hcmuaf.fit.travie_api.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.dto.BaseDTO;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppUserDTO extends BaseDTO {
    private String email;
    private String phone;
    private String role;
    private boolean emailVerified;
    private boolean phoneVerified;
    private boolean enabled;
    private UserInfoDTO userInfoDTO;
}
