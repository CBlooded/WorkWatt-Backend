package org.workwattbackend.usageHistory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.workwattbackend.computer.ComputerEntity;
import org.workwattbackend.computer.ComputerEntityRepository;
import org.workwattbackend.computer.ComputerService;
import org.workwattbackend.exception.ComputerNotPoweredOnException;
import org.workwattbackend.exception.UserNotOwnerOfComputerException;
import org.workwattbackend.usageHistory.dto.ComputerDto;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsageHistoryService {
    private final UsageHistoryRepository historyRepository;
    private final ComputerEntityRepository computerRepository;

    @Transactional
    public void startWork(ComputerDto computerDto){

        ComputerEntity computer = computerRepository.findById(computerDto.getComputerId()).orElseThrow();
        if(!computer.getUserId().equals(computerDto.getUserId()))
            throw new UserNotOwnerOfComputerException();

        UsageHistoryEntity history = UsageHistoryEntity.builder()
                .user_id(computerDto.getUserId())
                .start(LocalDateTime.now())
                .stop(null)
                .computerId(computerDto.getComputerId())
                .build();
        historyRepository.save(history);
    }

    @Transactional
    public void endWork(Long id){
        UsageHistoryEntity history = historyRepository.findByComputerIdAndStopIsNull(id).
                orElseThrow(() -> new ComputerNotPoweredOnException(id));
        history.setStop(LocalDateTime.now());
    }

}
