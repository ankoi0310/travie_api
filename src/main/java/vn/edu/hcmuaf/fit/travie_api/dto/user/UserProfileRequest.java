package vn.edu.hcmuaf.fit.travie_api.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.annotation.Phone;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.Gender;

import java.util.Date;

@Data
public class UserProfileRequest {
    @NotBlank
    private String fullName;

    @Phone
    private String phone;

    private Gender gender;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date birthday;
}
