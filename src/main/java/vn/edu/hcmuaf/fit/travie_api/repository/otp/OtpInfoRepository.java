package vn.edu.hcmuaf.fit.travie_api.repository.otp;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.travie_api.core.repository.CustomRepository;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;
import vn.edu.hcmuaf.fit.travie_api.entity.OtpInfo;

import java.util.Optional;

@NoRepositoryBean
public interface OtpInfoRepository extends CustomRepository<OtpInfo, String> {
    Optional<OtpInfo> findByOtp(String otp);

    Optional<OtpInfo> findByEmailAndType(String email, OTPType type);
}
