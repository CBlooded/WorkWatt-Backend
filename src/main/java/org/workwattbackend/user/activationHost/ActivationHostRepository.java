package org.workwattbackend.user.activationHost;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationHostRepository extends JpaRepository<ActivationHostEntity, String> {
    @Transactional
    void deleteByUserId(@NonNull String user_id);
}
