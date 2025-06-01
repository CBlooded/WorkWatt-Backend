package org.workwattbackend.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.workwattbackend.computer.ComputerEntityRepository;
import org.workwattbackend.hierarchy.HierarchyService;
import org.workwattbackend.usageHistory.UsageHistoryRepository;
import org.workwattbackend.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final UserRepository userRepository;
    private final UsageHistoryRepository usageRepository;
    private final ComputerEntityRepository computerRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final HierarchyService hierarchyService;

    public void sendUpdate(Message message, String topicIndex) {
        messagingTemplate.convertAndSend(String.format("/topic/updates/%s", topicIndex), message);
    }

    public void updateSupervisors(String userId) {
        System.out.println("UPdate sSSSS");
        var listOfSupervisorsIds = hierarchyService.getAllOfUsersSupervisors(userId);
        listOfSupervisorsIds.forEach(id -> this.sendUpdate(generateMessage(userId), id));
    }

    public void updateSupervisorsDeleteMessage(String userId) {
        var listOfSupervisorsIds = hierarchyService.getAllOfUsersSupervisors(userId);
        var listOfMessages = new ArrayList<Message>();
        listOfSupervisorsIds.forEach(i -> listOfMessages.add(generateMessage(userId)));
        for (Message listOfMessage : listOfMessages) listOfMessage.setDelete(true);

        for (int i = 0; i < listOfMessages.size(); i++) {
            sendUpdate(listOfMessages.get(i), listOfSupervisorsIds.get(i));
        }
    }

    public Message generateMessage(String userId) {
        System.out.println("Generate");
        String computerName = computerRepository.findByUserId(userId).orElseThrow().getName();
        var user = userRepository.findById(userId).orElseThrow();

        return Message.builder()
            .computerName(computerName)
            .userId(userId)
            .userFullName(user.getFirstName() + " " + user.getLastName())
            .start(LocalDateTime.now())
            .build();
    }
}