//package org.workwattbackend.security.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.workwattbackend.user.UserRepository;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApplicationConfig {
//    private final UserRepository userRepository;
//
//    //TODO userDetails, Authentication Manager, authentication provider
////    @Bean
////    public UserDetailsService userDetails() {
////        return id -> userRepository.findById(id).get();
////    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
