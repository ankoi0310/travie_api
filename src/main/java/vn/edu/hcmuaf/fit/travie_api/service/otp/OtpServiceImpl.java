package vn.edu.hcmuaf.fit.travie_api.service.otp;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.*;
import vn.edu.hcmuaf.fit.travie_api.core.infrastructure.mail.MailService;
import vn.edu.hcmuaf.fit.travie_api.core.shared.enums.OTPType;
import vn.edu.hcmuaf.fit.travie_api.entity.OtpInfo;
import vn.edu.hcmuaf.fit.travie_api.repository.otp.OtpInfoRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpInfoRepository otpInfoRepository;
    private final MailService mailService;

    @Override
    public String generateOTP(String email, OTPType type) {
        // Check if there is an existing OTP for the email
        otpInfoRepository.findByEmailAndType(email, type).ifPresent(otpInfoRepository::delete);

        // Generate a random 6-digit OTP and store it in the database
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(900000) + 100000;

        // Store the OTP in the database
        LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(type.getExpireTime());
        OtpInfo otpInfo = OtpInfo.builder()
                                 .email(email)
                                 .otp(String.valueOf(otp))
                                 .type(type)
                                 .expiredDate(expiredDate)
                                 .build();

        otpInfoRepository.save(otpInfo);

        return String.valueOf(otp);
    }

    @Async("taskExecutorForVerificationMail")
    @Override
    public CompletableFuture<Boolean> sendOTP(String fullName, String email, String otp, OTPType type) throws BaseException {
        CompletableFuture<Void> sendMail;

        return switch (type) {
            case VERIFICATION -> {
                sendMail = mailService.sendVerificationMail(fullName, email, otp);
                yield sendMail.thenApplyAsync(v -> true)
                              .exceptionally(e -> {
                                  log.error(e.getMessage());
                                  return false;
                              });
            }
            case RESET_PASSWORD -> {
                sendMail = mailService.sendResetPasswordMail(fullName, email, otp);
                yield sendMail.thenApplyAsync(v -> true)
                              .exceptionally(e -> {
                                  log.error(e.getMessage());
                                  return false;
                              });

            }
            default -> throw new ServiceBusinessException("Loại OTP không hợp lệ");
        };
    }

    @Override
    public void validateOTP(String otp) throws BaseException {
        try {
            // Check if the OTP is valid
            OtpInfo otpInfo = otpInfoRepository.findByOtp(otp)
                                               .orElseThrow(() -> new BadRequestException("OTP không hợp lệ"));

            if (otpInfo.getExpiredDate().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("OTP đã hết hạn");
            }

            otpInfoRepository.delete(otpInfo);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceBusinessException("Không thể xác thực OTP");
        }
    }
}
