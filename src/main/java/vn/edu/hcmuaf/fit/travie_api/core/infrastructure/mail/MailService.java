package vn.edu.hcmuaf.fit.travie_api.core.infrastructure.mail;

import vn.edu.hcmuaf.fit.travie_api.core.handler.exception.BaseException;

import java.util.concurrent.CompletableFuture;

public interface MailService {
    CompletableFuture<Void> sendVerificationMail(String name, String email, String otp) throws BaseException;

    CompletableFuture<Void> sendResetPasswordMail(String name, String email, String otp) throws BaseException;
}
