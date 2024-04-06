package vn.edu.hcmuaf.fit.travie_api.repository.otp;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.travie_api.core.repository.AbstractRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;
import vn.edu.hcmuaf.fit.travie_api.entity.OtpInfo;
import vn.edu.hcmuaf.fit.travie_api.entity.QOtpInfo;

import java.util.Optional;

@Repository
public class OtpInfoRepositoryImpl extends AbstractRepository<OtpInfo, String> implements OtpInfoRepository {
    private final QOtpInfo qOtpInfo = QOtpInfo.otpInfo;

    protected OtpInfoRepositoryImpl(EntityManager entityManager) {
        super(OtpInfo.class, entityManager);
    }

    @Override
    public Optional<OtpInfo> findByOtp(String otp) {
        return Optional.ofNullable(queryFactory.selectFrom(qOtpInfo)
                                               .where(qOtpInfo.otp.eq(otp))
                                               .fetchOne());
    }

    @Override
    public Optional<OtpInfo> findByEmailAndType(String email, OTPType type) {
        return Optional.ofNullable(queryFactory.selectFrom(qOtpInfo)
                                               .where(qOtpInfo.email.eq(email)
                                                                    .and(qOtpInfo.type.eq(type)))
                                               .fetchOne());
    }
}
