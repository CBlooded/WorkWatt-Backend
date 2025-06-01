package org.workwattbackend.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.workwattbackend.usageHistory.UsageHistoryRepository;

@Component
@RequiredArgsConstructor
public class MessageComponent {
    private final MessageService service;
    private final UsageHistoryRepository usageHistoryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        var listOfActiveUsers = usageHistoryRepository.findByStopIsNull();
        listOfActiveUsers.forEach(u -> service.updateSupervisors(u.getUser_id()));
    }
}
