package org.workwattbackend.authentication;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.workwattbackend.authentication.entities.AuthenticationRequest;
import org.workwattbackend.authentication.entities.AuthenticationResponse;
import org.workwattbackend.authentication.entities.RegisterRequest;
import org.workwattbackend.mailing.EmailController;
import org.workwattbackend.user.Role;
import org.workwattbackend.user.UserEntity;
import org.workwattbackend.user.UserRepository;
import org.workwattbackend.user.UserService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final EmailController emailController;
    private final UserService userService;
    //TODO call method for mail

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty())
            throw new RuntimeException();

        log.info("Request: {} {}", request.getEmail(), request.getPassword());

        try {
            authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getEmail(), //ArJAq9
                        request.getPassword()
                    )
                );
        } catch (Exception e) {
            //TODO exception account blocked
            //
            log.error(e.getMessage());
            throw new RuntimeException();
        }

        /*TODO
           Exception user not found
           generate token
         */
        return AuthenticationResponse.builder().token("1234").build();
    }

    public void register(RegisterRequest request) throws MessagingException {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new RuntimeException(); // TODO change to custom

        String temporaryPassword = generateTemporaryPassword();

        var user = UserEntity
            .builder()
            .id(UUID.randomUUID().toString())
            .email(request.getEmail())
            .password(encoder.encode(temporaryPassword))
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .role(Role.findByValue(request.getRole()))
            .enabled(false)
            .build();
        userRepository.save(user);

        userService.generateHostAndSendMail(user, temporaryPassword);

    }

    private String generateTemporaryPassword() {
        StringBuilder password = new StringBuilder();

        char[] characters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789").toCharArray();

        for (int i = 0; i < 6; i++)
            password.append(characters[(int) (Math.random() * characters.length)]);

        log.info("Generated password: {}", password.toString());

        return password.toString();
    }

}
