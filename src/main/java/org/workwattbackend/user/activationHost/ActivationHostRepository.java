package org.workwattbackend.user.activationHost;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ActivationHostRepository extends JpaRepository<ActivationHostEntity, String> {

    @Transactional
    long deleteByExpirationBefore(Date expirationBefore);

}
