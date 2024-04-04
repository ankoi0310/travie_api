package vn.edu.hcmuaf.fit.travie_api.repository.verificationtoken;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.QVerificationToken;
import vn.edu.hcmuaf.fit.travie_api.entity.VerificationToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public class VerificationTokenRepositoryImpl extends AbstractRepository<VerificationToken, Long> implements VerificationTokenRepository {
    private final QVerificationToken qVerificationToken = QVerificationToken.verificationToken;

    protected VerificationTokenRepositoryImpl(EntityManager entityManager) {
        super(VerificationToken.class, entityManager);
    }

    @Override
    public Optional<VerificationToken> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationToken)
                .where(qVerificationToken.email.eq(email))
                .fetchOne());
    }

    @Override
    public Optional<VerificationToken> findByToken(UUID token) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qVerificationToken)
                .where(qVerificationToken.token.eq(token))
                .fetchOne());
    }
}
