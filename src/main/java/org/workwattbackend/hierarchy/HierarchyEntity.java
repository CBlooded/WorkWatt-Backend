package org.workwattbackend.hierarchy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "hierarchy")
public class HierarchyEntity {
    @Id
    private long id;
    private String supervisorId;
    private String subordinateId;
}
