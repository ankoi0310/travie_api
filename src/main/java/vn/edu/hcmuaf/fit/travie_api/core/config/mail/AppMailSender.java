package vn.edu.hcmuaf.fit.travie_api.core.config.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AppMailSender {
    private final JavaMailSender javaMailSender;
    private MimeMessage message;

    @Autowired
    public AppMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String from, String to, String content, String subject, boolean html) throws MessagingException {
        message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(content, html);
        helper.setSubject(subject);

        javaMailSender.send(message);
    }

    public void sendMailWithImageInlined(String from, String to, String content, String subject, boolean html,
                                         byte[] imageInBytes) throws MessagingException {
        message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(content, html);
        helper.setSubject(subject);
        helper.addInline("image", new ByteArrayResource(imageInBytes), "image/png");

        javaMailSender.send(message);
    }
}
