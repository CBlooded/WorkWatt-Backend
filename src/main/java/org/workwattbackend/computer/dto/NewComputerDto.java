package org.workwattbackend.computer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class NewComputerDto {
    private String name;
    private double consumption;
    private int amount;

    public NewComputerDto(String name, double consumption, long amount) {
        this.name = name;
        this.consumption = consumption;
        this.amount = (int) amount;
    }
}
