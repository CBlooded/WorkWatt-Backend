package org.workwattbackend.hierarchy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class HierarchyEntity {
    @Id
    private long id;
    private String supervisorId;
    private String subordinateId;
}
