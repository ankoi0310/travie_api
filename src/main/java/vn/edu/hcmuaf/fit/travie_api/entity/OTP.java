package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.travie_api.core.entity.BaseEntity;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPState;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPType;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp")
public class OTP extends BaseEntity {
    private String code;
    private String email;
//    private String phone;

    @Convert(converter = OTPType.Converter.class)
    private OTPType type;

    @Convert(converter = OTPState.Converter.class)
    private OTPState state;

    private LocalDateTime sentAt;

    private LocalDateTime expiredAt;
}
