package org.workwattbackend.mailing;
//xX1BYgNg

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.workwattbackend.mailing.model.EmailData;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String hostEmail;

    public void sendMail(EmailData emailData) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(hostEmail);
        helper.setTo(emailData.getTo());
        helper.setSubject(emailData.getSubject());
        helper.setText(emailData.getHtmlBody(), true);

        mailSender.send(helper.getMimeMessage());
    }

}
