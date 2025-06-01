package org.workwattbackend.messaging;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.workwattbackend.user.UserEntity;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
public class Message {
    private long id;
    private String computerName;
    private String userFullName;
    private LocalDateTime start;

}
