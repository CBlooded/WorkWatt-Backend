package org.workwattbackend.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.workwattbackend.computer.ComputerEntity;
import org.workwattbackend.computer.ComputerEntityRepository;
import org.workwattbackend.usageHistory.UsageHistoryEntity;
import org.workwattbackend.usageHistory.UsageHistoryRepository;
import org.workwattbackend.user.UserEntity;
import org.workwattbackend.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final UsageHistoryRepository usageRepository;
    private final ComputerEntityRepository computerRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public void sendUpdate(Message message) {
        messagingTemplate.convertAndSend("/topic/updates", message);
    }

    public void sendMessages(List<Message> messages) {
        for (Message x : messages) {sendUpdate(x);}
    }

    public List<Message> generateMessages(){
        return usageRepository.findActiveUsageInfo();
//        List<UsageHistoryEntity> usages = usageRepository.findAllComputerIdAndStopIsNull();
//        List<ComputerEntity> computers = computerRepository.findAllById(
//                usages.stream().map(UsageHistoryEntity::getComputerId).collect(Collectors.toList()));
//        List<UserEntity> users = userRepository.findAllById(
//                computers.stream().map(ComputerEntity::getUserId).collect(Collectors.toList()));

    }
}