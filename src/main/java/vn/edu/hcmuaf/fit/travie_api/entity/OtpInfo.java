package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp")
public class OtpInfo {
    @Id
    @Column(unique = true)
    private String otp;

    private String email;

    @Enumerated(EnumType.STRING)
    private OTPType type;

    private LocalDateTime expiredDate;
}
