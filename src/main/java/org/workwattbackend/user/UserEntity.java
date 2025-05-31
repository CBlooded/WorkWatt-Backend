package org.workwattbackend.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    private String id;
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
