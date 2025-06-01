package org.workwattbackend.authentication;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.workwattbackend.authentication.entities.AuthenticationRequest;
import org.workwattbackend.authentication.entities.AuthenticationResponse;
import org.workwattbackend.authentication.entities.RegisterRequest;
import org.workwattbackend.exception.AccountNotActivatedException;
import org.workwattbackend.exception.UserAlreadyExistsException;
import org.workwattbackend.exception.UserNotFoundException;
import org.workwattbackend.mailing.EmailController;
import org.workwattbackend.security.config.JwtService;
import org.workwattbackend.user.Role;
import org.workwattbackend.user.UserEntity;
import org.workwattbackend.user.UserRepository;
import org.workwattbackend.user.UserService;

import java.util.UUID;

/**
 * The type Authentication service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final EmailController emailController;
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Authenticate authentication response.
     *
     * @param request the request
     * @return the authentication response
     * @throws UserNotFoundException        the user not found exception
     * @throws AccountNotActivatedException the account not activated exception
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException, AccountNotActivatedException {
        var user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty())
            throw new UserNotFoundException();

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );


        UserDetails userDetails = userRepository.findByEmail(request.getEmail()).orElseThrow();

        return AuthenticationResponse.builder().token(jwtService.generateTokenWithExtraClaims(userDetails)).build();
    }

    /**
     * Register.
     *
     * @param request the request
     * @throws MessagingException         the messaging exception
     * @throws UserAlreadyExistsException the user already exists exception
     */
    public void register(RegisterRequest request) throws MessagingException, UserAlreadyExistsException {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new UserAlreadyExistsException();

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
