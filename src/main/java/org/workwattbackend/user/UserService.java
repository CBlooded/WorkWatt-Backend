package org.workwattbackend.user;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.workwattbackend.exception.HostNotFoundException;
import org.workwattbackend.exception.UserAlreadyExistsException;
import org.workwattbackend.exception.UserNotFoundException;
import org.workwattbackend.mailing.EmailService;
import org.workwattbackend.mailing.model.EmailData;
import org.workwattbackend.mailing.model.HttpMailBody;
import org.workwattbackend.user.activationHost.ActivationHostEntity;
import org.workwattbackend.user.activationHost.ActivationHostRepository;

import java.util.Calendar;
import java.util.Objects;
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
        var host = ActivationHostEntity.builder().id(UUID.randomUUID().toString()).userId(user.getId()).build();

        hostRepository.save(host);

        this.sentAccountConfirmationMail(temporaryPassword, host);
    }


    /**
     * Change password and activate.
     *
     * @param userId      the user id
     * @param newPassword the new password
     */
    public void changePasswordAndActivate(String userId, String newPassword) throws UserNotFoundException {
        var user = repository.findById(userId).orElseThrow(UserNotFoundException::new);
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
    public UserEntity getUserFromHostId(String hostId) throws HostNotFoundException, UserNotFoundException {
        var host = hostRepository.findById(hostId).orElseThrow(HostNotFoundException::new);
        return userRepository.findById(host.getUserId()).orElseThrow(UserNotFoundException::new);
    }

    public boolean isHostIdValid(String hostId) {
        return hostRepository.findById(hostId).isPresent();
    }

    public boolean isTempPasswordValid(String userId, String tempPassword) {
        var user = repository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (encoder.matches(user.getPassword(), tempPassword)) {
            log.info("Correct");
            return true;
        }
        return false;
    }

    private void sentAccountConfirmationMail(String temporaryPassword, ActivationHostEntity host) throws MessagingException, UserNotFoundException {
        var user = repository.findById(host.getUserId()).orElseThrow(UserNotFoundException::new);

        var mailBody = EmailData.builder().to(user.getEmail()).subject("WorkWatt: Activate your account").htmlBody(HttpMailBody.getActivationMail(user, temporaryPassword, host.getId())).build();

        emailService.sendMail(mailBody);
    }


}
