package vn.edu.hcmuaf.fit.travie_api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.annotation.Phone;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.user.Gender;

import java.time.LocalDate;

@Data
public class UserProfileRequest {
    @NotBlank
    private String username;

    @Phone
    private String phone;

    @NotBlank
    private String fullName;

    private Gender gender;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private LocalDate birthday;
}
