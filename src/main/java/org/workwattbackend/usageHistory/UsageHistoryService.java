package org.workwattbackend.usageHistory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.workwattbackend.computer.ComputerEntity;
import org.workwattbackend.computer.ComputerEntityRepository;
import org.workwattbackend.exception.ComputerNotPoweredOnException;
import org.workwattbackend.hierarchy.HierarchyRepository;
import org.workwattbackend.usageHistory.dto.ComputerDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsageHistoryService {
    private final UsageHistoryRepository historyRepository;
    private final ComputerEntityRepository computerEntityRepository;
    private final HierarchyRepository hierarchyRepository;

    public Map<String, List<String>> getUserUsageHistoryChartData(LocalDateTime start, LocalDateTime stop, String userId) {
        List<String> x = new ArrayList<>();
        List<String> y = new ArrayList<>();
        var dataList = historyRepository.findByDateRangeAndUser(start, stop, userId);

        List<LocalDateTime> segments = divideTime(start, stop, 10);

        double consumption = computerEntityRepository
            .findByUserId(userId)
            .orElseThrow(RuntimeException::new)
            .getConsumption();

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime segmentStart = segments.get(i);
            LocalDateTime segmentEnd = segments.get(i + 1);

            x.add(segmentStart.format(DateTimeFormatter.ofPattern("dd:MM:yy")));

            float sumHours = 0f;

            for (var record : dataList) {
                LocalDateTime recordStart = record.getStart();
                LocalDateTime recordEnd = record.getStop();

                if (!(recordEnd.isBefore(segmentStart) || recordStart.isAfter(segmentEnd))) {

                    LocalDateTime maxStart = recordStart.isAfter(segmentStart) ? recordStart : segmentStart;
                    LocalDateTime minEnd = recordEnd.isBefore(segmentEnd) ? recordEnd : segmentEnd;

                    Duration overlap = Duration.between(maxStart, minEnd);
                    long seconds = overlap.isNegative() ? 0 : overlap.getSeconds();

                    sumHours += seconds / 3600f;
                }
            }

            y.add(String.valueOf(sumHours * consumption));
        }


        Map<String, List<String>> result = new HashMap<>();
        result.put("X", x);
        result.put("Y", y);
        return result;
    }

    public Map<String, List<String>> getSupervisorUsageChartSummed(LocalDateTime start, LocalDateTime stop, String supervisorId) {
        List<String> x = new ArrayList<>();
        List<String> y = new ArrayList<>();

        Set<String> userIds = getAllSubordinates(supervisorId);

        var dataList = historyRepository.findByDateRangeAndUsers(start, stop, userIds);

        List<LocalDateTime> segments = divideTime(start, stop, 10);

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime segmentStart = segments.get(i);
            LocalDateTime segmentEnd = segments.get(i + 1);

            x.add(segmentStart.format(DateTimeFormatter.ofPattern("dd:MM:yy")));

            float segmentTotal = 0f;

            for (var record : dataList) {
                LocalDateTime recordStart = record.getStart();
                LocalDateTime recordEnd = record.getStop();
                String userId = record.getUser_id();

                if (!(recordEnd.isBefore(segmentStart) || recordStart.isAfter(segmentEnd))) {
                    LocalDateTime maxStart = recordStart.isAfter(segmentStart) ? recordStart : segmentStart;
                    LocalDateTime minEnd = recordEnd.isBefore(segmentEnd) ? recordEnd : segmentEnd;

                    Duration overlap = Duration.between(maxStart, minEnd);
                    long seconds = overlap.isNegative() ? 0 : overlap.getSeconds();

                    double consumption = computerEntityRepository
                        .findByUserId(userId)
                        .map(ComputerEntity::getConsumption)
                        .orElse(0.0);

                    segmentTotal += seconds / 3600f * (float) consumption;
                }
            }

            y.add(String.format("%.2f", segmentTotal));
        }

        Map<String, List<String>> result = new HashMap<>();
        result.put("X", x);
        result.put("Y", y);
        return result;
    }

    public Map<String, Object> getSupervisorUsageChart(LocalDateTime start, LocalDateTime stop, String supervisorId) {
        List<LocalDateTime> segments = divideTime(start, stop, 10);
        List<String> x = segments.stream()
            .limit(segments.size() - 1)
            .map(d -> d.format(DateTimeFormatter.ofPattern("dd:MM:yy")))
            .toList();

        Set<String> userIds = getAllSubordinates(supervisorId);
        var dataList = historyRepository.findByDateRangeAndUsers(start, stop, userIds);

        // Map: userId -> List of values per segment
        Map<String, List<String>> usagePerUser = new HashMap<>();

        // Initialize empty lists for each subordinate
        for (String userId : userIds) {
            usagePerUser.put(userId, new ArrayList<>());
        }

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime segmentStart = segments.get(i);
            LocalDateTime segmentEnd = segments.get(i + 1);

            // Map userId -> sum usage for this segment
            Map<String, Float> usageThisSegment = new HashMap<>();
            for (String userId : userIds) {
                usageThisSegment.put(userId, 0f);
            }

            for (var record : dataList) {
                LocalDateTime recordStart = record.getStart();
                LocalDateTime recordEnd = record.getStop();
                String userId = record.getUser_id();

                if (!(recordEnd.isBefore(segmentStart) || recordStart.isAfter(segmentEnd))) {
                    LocalDateTime maxStart = recordStart.isAfter(segmentStart) ? recordStart : segmentStart;
                    LocalDateTime minEnd = recordEnd.isBefore(segmentEnd) ? recordEnd : segmentEnd;

                    Duration overlap = Duration.between(maxStart, minEnd);
                    long seconds = overlap.isNegative() ? 0 : overlap.getSeconds();

                    double consumption = computerEntityRepository
                        .findByUserId(userId)
                        .map(ComputerEntity::getConsumption)
                        .orElse(0.0);

                    float value = seconds / 3600f * (float) consumption;
                    usageThisSegment.put(userId, usageThisSegment.getOrDefault(userId, 0f) + value);
                }
            }

            // Add the usage for each user for this segment to their list
            for (String userId : userIds) {
                usagePerUser.get(userId).add(String.format("%.2f", usageThisSegment.get(userId)));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("X", x);
        result.put("Y", usagePerUser);

        return result;
    }



    public void startWork(ComputerDto computerDto) {
        UsageHistoryEntity history = UsageHistoryEntity.builder().user_id(computerDto.getUserId()).start(LocalDateTime.now()).stop(null).computerId(computerDto.getComputerId()).build();
        historyRepository.save(history);
    }

    @Transactional
    public void endWork(Long id) {
        UsageHistoryEntity history = historyRepository.findByComputerIdAndStopIsNull(id).orElseThrow(() -> new ComputerNotPoweredOnException(id));
        history.setStop(LocalDateTime.now());
    }

    private Set<String> getAllSubordinates(String supervisorId) {
        Set<String> result = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(supervisorId);

        while (!queue.isEmpty()) {
            String currentSupervisor = queue.poll();
            List<String> subordinates = hierarchyRepository.findSubordinatesBySupervisor(currentSupervisor);
            for (String subordinate : subordinates) {
                if (result.add(subordinate)) {
                    queue.add(subordinate);
                }
            }
        }

        return result;
    }

    private List<LocalDateTime> divideTime(LocalDateTime startDate, LocalDateTime endDate, int parts) {
        List<LocalDateTime> points = new ArrayList<>();

        if (startDate.isAfter(endDate))
            return new ArrayList<>();


        Duration totalDuration = Duration.between(startDate, endDate);
        Duration step = totalDuration.dividedBy(parts);

        for (int i = 0; i <= parts; i++) {
            LocalDateTime point = startDate.plus(step.multipliedBy(i));
            points.add(point);
        }

        return points;
    }


}
