package org.workwattbackend.user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    @NonNull
    Optional<UserEntity> findById(@NonNull String id);
}
