package org.workwattbackend.mailing.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class EmailData {
    private final String to;
    private final String subject;
    private final String htmlBody;
}
