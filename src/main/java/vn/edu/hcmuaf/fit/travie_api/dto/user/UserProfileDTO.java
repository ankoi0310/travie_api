package vn.edu.hcmuaf.fit.travie_api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.user.Gender;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String nickname;
    private String email;
    private String phone;
    private Gender gender;
    private String avatar;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate birthday;
}
