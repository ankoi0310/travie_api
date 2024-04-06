package vn.edu.hcmuaf.fit.travie_api.core.config.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AppMailSender {
    private final JavaMailSender javaMailSender;

    public void sendMail(String from, String to, String content, String subject, boolean html) throws MessagingException,
            UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        createMimeMessageHelper(message, from, to, content, subject, html);
        javaMailSender.send(message);
    }

    public void sendMailWithInlinedImage(String from, String to, String content, String subject, boolean html,
                                         byte[] imageInBytes) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = createMimeMessageHelper(message, from, to, content, subject, html);
        helper.addInline("image", new ByteArrayResource(imageInBytes), "image/png");

        javaMailSender.send(message);
    }

    private MimeMessageHelper createMimeMessageHelper(MimeMessage message, String from, String to, String content,
                                                      String subject, boolean html) throws MessagingException,
            UnsupportedEncodingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom(from, "Travie Team");
        helper.setTo(to);
        helper.setText(content, html);
        helper.setSubject(subject);

        return helper;
    }
}
