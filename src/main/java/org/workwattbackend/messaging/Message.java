package org.workwattbackend.messaging;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class Message {
    @Setter
    private boolean delete = false;
    private final String computerName;
    private final String userId;
    private final String userFullName;
    private final LocalDateTime start;
}
