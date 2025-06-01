package org.workwattbackend.usageHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.workwattbackend.messaging.Message;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public interface UsageHistoryRepository extends JpaRepository<UsageHistoryEntity, Long> {
    Optional<UsageHistoryEntity> findByComputerIdAndStopIsNull(Long computerId);

    @Query("SELECT u FROM UsageHistoryEntity u WHERE u.start >= :start AND u.stop <= :stop AND u.user_id = :userId")
    List<UsageHistoryEntity> findByDateRangeAndUser(
        @Param("start") LocalDateTime start,
        @Param("stop") LocalDateTime stop,
        @Param("userId") String userId
    );

    @Query("SELECT h FROM UsageHistoryEntity h " +
        "WHERE h.user_id IN :userIds " +
        "AND h.start <= :stop " +
        "AND h.stop >= :start")
    List<UsageHistoryEntity> findByDateRangeAndUsers(
        @Param("start") LocalDateTime start,
        @Param("stop") LocalDateTime stop,
        @Param("userIds") Collection<String> userIds
    );

    @Query("SELECT new org.workwattbackend.messaging.Message(u.id, c.name, CONCAT(us.firstName, ' ', us.lastName), u.start) " +
            "FROM UsageHistoryEntity u " +
            "JOIN ComputerEntity c ON u.computerId = c.id " +
            "JOIN UserEntity us ON c.userId = us.id " +
            "WHERE u.stop IS NULL")
    List<Message> findActiveUsageInfo();

}