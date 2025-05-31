package org.workwattbackend.usageHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.workwattbackend.usageHistory.dto.ComputerDto;
import org.workwattbackend.user.UserRepository;

@RestController
@RequiredArgsConstructor
public class UsageHistoryController {
    private final UserRepository userRepository;
    private final UsageHistoryService usageService;

    @GetMapping("/usageHistory")
    public ResponseEntity<?> getUsageHistory(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/startWork")
    public ResponseEntity<Void> startWork(@RequestBody ComputerDto computerDto){
        usageService.startWork(computerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/endWork")
    public ResponseEntity<Void> endWork(@RequestBody Long computerId){
        usageService.endWork(computerId);
        return ResponseEntity.ok().build();
    }
}
