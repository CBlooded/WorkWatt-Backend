package org.workwattbackend.user;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.workwattbackend.mailing.EmailService;
import org.workwattbackend.mailing.model.EmailData;
import org.workwattbackend.mailing.model.HttpMailBody;
import org.workwattbackend.user.activationHost.ActivationHostEntity;
import org.workwattbackend.user.activationHost.ActivationHostRepository;

import java.util.Calendar;
import java.util.UUID;

/**
 * The type User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final EmailService emailService;
    private final UserRepository repository;
    private final ActivationHostRepository hostRepository;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    /**
     * Generate host and send mail.
     *
     * @param user              the user
     * @param temporaryPassword the temporary password
     * @throws MessagingException the messaging exception
     */
    public void generateHostAndSendMail(UserEntity user, String temporaryPassword) throws MessagingException {
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.MINUTE, 10);

        var host = ActivationHostEntity.builder().id(UUID.randomUUID().toString()).userId(user.getId()).expiration(expirationTime.getTime()).build();

        hostRepository.save(host);

        this.sentAccountConfirmationMail(temporaryPassword, host);
    }


    /**
     * Change password and activate.
     *
     * @param userId      the user id
     * @param newPassword the new password
     */
    public void changePasswordAndActivate(String userId, String newPassword) {
        var user = repository.findById(userId).orElseThrow(RuntimeException::new); //TODO change to custom exception
        user.setPassword(encoder.encode(newPassword));
        user.setEnabled(true);
        repository.save(user);
    }

    /**
     * Gets user from host id.
     *
     * @param hostId the host id
     * @return the user from host id
     */
    public UserEntity getUserFromHostId(String hostId) {
        var host = hostRepository.findById(hostId).orElseThrow(RuntimeException::new);//TODO change to custom
        return userRepository.findById(host.getUserId()).orElseThrow(RuntimeException::new);//TODO change to custom
    }

    private void sentAccountConfirmationMail(String temporaryPassword, ActivationHostEntity host) throws MessagingException {
        var user = repository.findById(host.getUserId()).orElseThrow(RuntimeException::new); //TODO change to custom exception

        var mailBody = EmailData.builder().to(user.getEmail()).subject("WorkWatt: Activate your account").htmlBody(HttpMailBody.getActivationMail(user, temporaryPassword, host.getId())).build();

        emailService.sendMail(mailBody);
    }

}
