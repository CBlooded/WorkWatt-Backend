package org.workwattbackend.hierarchy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HierarchyRepository extends JpaRepository<HierarchyEntity, Long> {
    @Query("SELECT h.subordinateId FROM HierarchyEntity h WHERE h.supervisorId = :supervisorId")
    List<String> findSubordinatesBySupervisor(@Param("supervisorId") String supervisorId);

    @Query("SELECT h.supervisorId FROM HierarchyEntity h WHERE h.subordinateId = :subordinateId")
    List<String> findSupervisorsBySubordinateId(@Param("subordinateId") String subordinateId);
}
