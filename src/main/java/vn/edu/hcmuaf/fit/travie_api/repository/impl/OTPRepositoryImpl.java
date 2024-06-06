package vn.edu.hcmuaf.fit.travie_api.repository.impl;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.otp.OTPState;
import vn.edu.hcmuaf.fit.travie_api.entity.OTP;
import vn.edu.hcmuaf.fit.travie_api.entity.QOTP;
import vn.edu.hcmuaf.fit.travie_api.repository.OTPRepository;

import java.util.Optional;

@Repository
public class OTPRepositoryImpl extends AbstractRepository<OTP, Long> implements OTPRepository {
    private final QOTP qOTP = QOTP.oTP;

    protected OTPRepositoryImpl(EntityManager entityManager) {
        super(OTP.class, entityManager);
    }

    @Override
    public Optional<OTP> findByCode(String code) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qOTP)
                .where(qOTP.code.eq(code))
                .fetchOne());
    }

    @Override
    public Optional<OTP> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .selectFrom(qOTP)
                .where(qOTP.email.eq(email))
                .fetchOne());
    }

    @Override
    public void delete() {
        queryFactory
                .delete(qOTP)
                .where(qOTP.state.eq(OTPState.EXPIRED)
                                 .or(qOTP.state.eq(OTPState.VERIFIED)))
                .execute();
    }
}
