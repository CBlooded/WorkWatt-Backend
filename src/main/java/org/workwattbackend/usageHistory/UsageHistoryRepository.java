package org.workwattbackend.usageHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsageHistoryRepository extends JpaRepository<UsageHistoryEntity, Long> {
    Optional<UsageHistoryEntity> findByComputerIdAndStopIsNull(Long computerId);

    @Query("SELECT u FROM UsageHistoryEntity u WHERE u.start >= :start AND u.stop <= :stop AND u.user_id = :userId")
    List<UsageHistoryEntity> findByDateRangeAndUser(
        @Param("start") LocalDateTime start,
        @Param("stop") LocalDateTime stop,
        @Param("userId") String userId
    );
}