package org.workwattbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorkWattBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkWattBackendApplication.class, args);
    }

}
