package org.workwattbackend.usageHistory.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ComputerDto {
    private String userId;
    private Long computerId;
}
