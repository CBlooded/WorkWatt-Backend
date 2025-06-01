package org.workwattbackend.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder
public class Message {
    private final String computerName;
    private final String userId;
    private final String userFullName;
    private final LocalDateTime start;
}
