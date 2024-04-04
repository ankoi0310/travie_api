package vn.edu.hcmuaf.fit.travie_api.repository.resetpasswordtoken;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.ResetPasswordToken;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface ResetPasswordTokenRepository extends CustomRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(UUID token);
}
