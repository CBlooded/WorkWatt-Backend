package org.workwattbackend.usageHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsageHistoryRepository extends JpaRepository<UsageHistoryEntity, Long> {
    Optional<UsageHistoryEntity> findByComputerIdAndStopIsNull(Long computerId);
}