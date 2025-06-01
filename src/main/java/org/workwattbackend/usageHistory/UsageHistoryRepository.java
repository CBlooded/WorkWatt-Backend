package org.workwattbackend.usageHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsageHistoryRepository extends JpaRepository<UsageHistoryEntity, Long> {
    Optional<UsageHistoryEntity> findByComputerIdAndStopIsNull(Long computerId);

    List<UsageHistoryEntity> findByStartGreaterThanEqualAndStopLessThanEqualAndUser_id(LocalDateTime startDate, LocalDateTime endDate, String userId);

}