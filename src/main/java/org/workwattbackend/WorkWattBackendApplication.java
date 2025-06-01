package org.workwattbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.workwattbackend.messaging.MessageService;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class WorkWattBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(WorkWattBackendApplication.class, args);


    }

}
