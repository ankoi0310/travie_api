package vn.edu.hcmuaf.fit.travie_api.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String phone;
    private String address;
    private String avatar;
    private String role;
    private String status;
    private String fullName;
}
