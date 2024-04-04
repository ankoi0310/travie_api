package vn.edu.hcmuaf.fit.travie_api.repository.verificationtoken;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.VerificationToken;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface VerificationTokenRepository extends CustomRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByEmail(String email);

    Optional<VerificationToken> findByToken(UUID token);
}
