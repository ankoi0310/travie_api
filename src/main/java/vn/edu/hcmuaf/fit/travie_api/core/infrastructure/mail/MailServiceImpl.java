package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.fit.travie_api.core.config.mail.AppMailSender;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.ServiceUnavailableException;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    @Value("${app.mail.no-reply}")
    private String from;

    private final AppMailSender appMailSender;

    public CompletableFuture<Void> sendVerificationMail(String name, String email, String otp) throws BaseException {
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            String content = "Hello " + name + ",\n\n" +
                    "Please verify your email address by entering the following code: " + otp + "\n\n" +
                    "If you did not sign up for an account, please ignore this email.\n\n" +
                    "Thank you,\n" +
                    "Travie Team";
            appMailSender.sendMail(from, email, content, "Verify your email address", false);

            future.complete(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            future.completeExceptionally(new ServiceUnavailableException("Failed to send verification email"));
        }

        return future;
    }

    @Override
    public CompletableFuture<Void> sendResetPasswordMail(String name, String email, String otp) throws BaseException {
        return null;
    }
}
