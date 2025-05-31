package org.workwattbackend.user;

import lombok.Getter;

@Getter
public enum Role {
    DIRECTOR(0), MANAGER(1), EMPLOYEE(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

}
