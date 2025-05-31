package org.workwattbackend.user.activationHost;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The type Activation host scheduler.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ActivationHostScheduler {
    private final ActivationHostRepository repository;

    /**
     * Delete expired hosts.
     */
    @Scheduled(fixedRate = 600000)
    public void deleteExpiredHosts() {
        long amount = repository.deleteByExpirationBefore(new Date());
        log.info("# LOG: Successfully deleted {} expired hosts.", amount);
    }
}
