package vn.edu.hcmuaf.fit.travie_api.repository.resetpasswordtoken;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.entity.QResetPasswordToken;
import vn.edu.hcmuaf.fit.travie_api.entity.ResetPasswordToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ResetPasswordTokenRepositoryImpl extends AbstractRepository<ResetPasswordToken, Long>
        implements ResetPasswordTokenRepository {
    private final QResetPasswordToken qEntity = QResetPasswordToken.resetPasswordToken;

    protected ResetPasswordTokenRepositoryImpl(EntityManager entityManager) {
        super(ResetPasswordToken.class, entityManager);
    }

    @Override
    public Optional<ResetPasswordToken> findByToken(UUID token) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qEntity)
                .where(qEntity.token.eq(token))
                .fetchOne());
    }
}
