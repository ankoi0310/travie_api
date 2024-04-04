package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.mail;

import org.springframework.scheduling.annotation.Async;
import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BadRequestException;

public interface MailService {

    @Async("threadPoolTaskExecutorForVerificationMail")
    void sendVerificationMail(String name, String email, String token) throws BadRequestException;
}
