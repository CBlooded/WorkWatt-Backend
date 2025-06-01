package org.workwattbackend.usageHistory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workwattbackend.usageHistory.dto.ComputerDto;
import org.workwattbackend.user.UserRepository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * The type Usage history controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/usage")
@RequiredArgsConstructor
public class UsageHistoryController {
    private final UsageHistoryService usageService;

    /**
     * Get usage history response entity.
     *
     * @param start  the start
     * @param end    the end
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/history")
    public ResponseEntity<List<UsageHistoryEntity>> getUsageHistoryForSingleUSer(@RequestParam(name = "s") String start, @RequestParam(name = "e") String end, @RequestParam(name = "u") String userId) {
        long startMilis = Long.parseLong(start);
        long stopMilis = Long.parseLong(end);
        var list = usageService.getUserUsageHistory(new Date(startMilis), new Date(stopMilis), userId);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    /**
     * Start work response entity.
     *
     * @param computerDto the computer dto
     * @return the response entity
     */
    @PostMapping("/startWork")
    public ResponseEntity<Void> startWork(@RequestBody ComputerDto computerDto) {
        usageService.startWork(computerDto);
        return ResponseEntity.ok().build();
    }

    /**
     * End work response entity.
     *
     * @param computerId the computer id
     * @return the response entity
     */
    @PostMapping("/endWork")
    public ResponseEntity<Void> endWork(@RequestBody Long computerId) {
        usageService.endWork(computerId);
        return ResponseEntity.ok().build();
    }
}
