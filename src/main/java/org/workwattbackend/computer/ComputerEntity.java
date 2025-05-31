package org.workwattbackend.computer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class ComputerEntity {
    @Id
    private long id;
    private String name;
    private String userId;
    private double consumption;

}
