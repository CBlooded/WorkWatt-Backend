package org.workwattbackend.usageHistory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workwattbackend.usageHistory.dto.ComputerDto;
import org.workwattbackend.user.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * The type Usage history controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/usage")
@RequiredArgsConstructor
@CrossOrigin
public class UsageHistoryController {
    private final UsageHistoryService usageService;
    private final UserService userService;

    /**
     * Get usage history response entity.
     *
     * @param start  the start
     * @param end    the end
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/history")
    public ResponseEntity<Map<String, List<String>>> getUsageHistoryForSingleUSer(@RequestParam(name = "s") String start, @RequestParam(name = "e") String end, @RequestParam(name = "u") String userId) {
        Instant startMilis = Instant.ofEpochMilli(Long.parseLong(start));
        Instant stopMilis = Instant.ofEpochMilli(Long.parseLong(end));

        var list = usageService.getUserUsageHistoryChartData(LocalDateTime.ofInstant(startMilis, ZoneId.systemDefault()), LocalDateTime.ofInstant(stopMilis, ZoneId.systemDefault()), userId);
        if (list.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(list);
    }


    /**
     * Gets usage history for supervisor.
     *
     * @param start        the start
     * @param end          the end
     * @param supervisorId the supervisor id
     * @return the usage history for supervisor
     */
    @GetMapping("/supervisor/history")
    public ResponseEntity<Map<String, Object>> getUsageHistoryForSupervisor(
        @RequestParam(name = "s") String start,
        @RequestParam(name = "e") String end,
        @RequestParam(name = "supervisor") String supervisorId) {

        Instant startMillis = Instant.ofEpochMilli(Long.parseLong(start));
        Instant stopMillis = Instant.ofEpochMilli(Long.parseLong(end));

        LocalDateTime startDateTime = LocalDateTime.ofInstant(startMillis, ZoneId.systemDefault());
        LocalDateTime stopDateTime = LocalDateTime.ofInstant(stopMillis, ZoneId.systemDefault());

        Map<String, Object> chartData = usageService.getSupervisorUsageChart(startDateTime, stopDateTime, supervisorId);

        if (chartData == null || chartData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(chartData);
    }

    @GetMapping("/supervisor/history/summed")
    public ResponseEntity<Map<String, List<String>>> getUsageHistoryForSupervisorGrouped(
        @RequestParam(name = "s") String start,
        @RequestParam(name = "e") String end,
        @RequestParam(name = "supervisor") String supervisorId) {

        Instant startMillis = Instant.ofEpochMilli(Long.parseLong(start));
        Instant stopMillis = Instant.ofEpochMilli(Long.parseLong(end));

        LocalDateTime startDateTime = LocalDateTime.ofInstant(startMillis, ZoneId.systemDefault());
        LocalDateTime stopDateTime = LocalDateTime.ofInstant(stopMillis, ZoneId.systemDefault());

        Map<String, List<String>> chartData = usageService.getSupervisorUsageChartSummed(startDateTime, stopDateTime, supervisorId);

        if (chartData == null || chartData.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(chartData);
    }


    /**
     * Start work response entity.
     *
     * @param computerDto the computer dto
     * @return the response entity
     */
    @PostMapping("/desktop/startWork")
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
    @PostMapping("/desktop/endWork")
    public ResponseEntity<Void> endWork(@RequestBody Long computerId) {
        usageService.endWork(computerId);
        return ResponseEntity.ok().build();
    }
}
