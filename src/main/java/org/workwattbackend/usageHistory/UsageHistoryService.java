package org.workwattbackend.usageHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.workwattbackend.exception.ComputerNotPoweredOnException;
import org.workwattbackend.usageHistory.dto.ComputerDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsageHistoryService {
    private final UsageHistoryRepository historyRepository;

    public List<UsageHistoryEntity> getUserUsageHistory(Date start, Date stop, String userId) {
        return historyRepository.findByDateRangeAndUser(start, stop, userId);
    }


    public void startWork(ComputerDto computerDto) {
        UsageHistoryEntity history = UsageHistoryEntity.builder()
            .user_id(computerDto.getUserId())
            .start(LocalDateTime.now())
            .stop(null)
            .computerId(computerDto.getComputerId())
            .build();
        historyRepository.save(history);
    }

    public void endWork(Long id) {
        UsageHistoryEntity history = historyRepository.findByComputerIdAndStopIsNull(id).
            orElseThrow(() -> new ComputerNotPoweredOnException(id));
        history.setStop(LocalDateTime.now());
    }

}
