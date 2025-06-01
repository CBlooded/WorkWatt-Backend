package org.workwattbackend.usageHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.workwattbackend.exception.ComputerNotPoweredOnException;
import org.workwattbackend.usageHistory.dto.ComputerDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UsageHistoryService {
    private final UsageHistoryRepository historyRepository;

    public Map<String, List<String>> getUserUsageHistoryChartData(LocalDateTime start, LocalDateTime stop, String userId) {
        List<String> x = new ArrayList<>();
        List<String> y = new ArrayList<>();
        var dataList = historyRepository.findByDateRangeAndUser(start, stop, userId);

        List<LocalDateTime> segments = divideTime(start, stop, 10);

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

            y.add(String.valueOf(sumHours));
        }


        Map<String, List<String>> result = new HashMap<>();
        result.put("X", x);
        result.put("Y", y);
        return result;
    }


    public void startWork(ComputerDto computerDto) {
        UsageHistoryEntity history = UsageHistoryEntity.builder().user_id(computerDto.getUserId()).start(LocalDateTime.now()).stop(null).computerId(computerDto.getComputerId()).build();
        historyRepository.save(history);
    }

    public void endWork(Long id) {
        UsageHistoryEntity history = historyRepository.findByComputerIdAndStopIsNull(id).orElseThrow(() -> new ComputerNotPoweredOnException(id));
        history.setStop(LocalDateTime.now());
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
