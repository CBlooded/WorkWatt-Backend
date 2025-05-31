package org.workwattbackend.computer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.workwattbackend.computer.dto.NewComputerDto;

import java.util.List;
import java.util.Optional;

public interface ComputerEntityRepository extends JpaRepository<ComputerEntity, Long> {

    @Query("SELECT new org.workwattbackend.computer.dto.NewComputerDto(c.name, c.consumption, COUNT(c)) " +
            "FROM ComputerEntity c " +
            "WHERE c.userId IS NULL " +
            "GROUP BY c.name, c.consumption")
    List<NewComputerDto> findFreeComputers();

    Optional<ComputerEntity> findFirstByNameAndUserIdIsNullOrderByIdAsc(String name);
}
