package vn.edu.hcmuaf.fit.travie_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID token;

    private LocalDateTime expiredDate;
    private boolean isExpired;
}
