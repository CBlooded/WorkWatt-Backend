package org.workwattbackend.authentication.entities;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.workwattbackend.user.Role;

@Data
@Getter
@RequiredArgsConstructor
public class RegisterRequest {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final int role;
}
