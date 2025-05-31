package org.workwattbackend.mailing;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.workwattbackend.mailing.model.EmailData;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/mailing")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService service;

    //TODO TEMP
    @GetMapping
    public ResponseEntity<Void> sendTestMail() throws MessagingException {
        service.sendMail(
            EmailData
                .builder()
                .to("mikiflor24@gmail.com")
                .subject("test")
                .htmlBody("<h1>Hello</h1>")
                .build()
        );
        return ResponseEntity.ok().build();
    }
}
