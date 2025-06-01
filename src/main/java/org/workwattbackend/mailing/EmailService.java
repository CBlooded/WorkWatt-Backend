package org.workwattbackend.mailing;
//xX1BYgNg

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.workwattbackend.mailing.model.EmailData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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


    public String loadHtmlTemplate(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error in html file", e);
        }
    }


    @Scheduled(cron = "0 50 11 * * *")
    public void dayReport() throws MessagingException {
        String htmlContent = loadHtmlTemplate("src/main/resources/templates/mail.html");

        EmailData mail = EmailData.builder()
                .to("royopat140@jeanssi.com")
                .subject("Report " + LocalDateTime.now().toString())
                .htmlBody(htmlContent).build();
        sendMail(mail);
    }

}
