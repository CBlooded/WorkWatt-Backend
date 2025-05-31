package org.workwattbackend.computer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.workwattbackend.computer.dto.NewComputerDto;
import org.workwattbackend.computer.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerService {
    private final ComputerEntityRepository computerRepository;

    @Transactional
    public void addComputers(NewComputerDto newComputer) {
        List<ComputerEntity> comps = new ArrayList<>();
        for (int i = 0; i < newComputer.getAmount(); i++) {
            comps.add(ComputerEntity.builder()
                    .name(newComputer.getName())
                    .userId(null)
                    .consumption(newComputer.getConsumption())
                    .build());
        }
        computerRepository.saveAll(comps);
    }

    public List<NewComputerDto> getFreeComputers() {
        return computerRepository.findFreeComputers();
    }

    @Transactional
    public void assignUser(UserDto userDto){
        ComputerEntity comp = computerRepository.findFirstByNameOrderByIdAsc(userDto.getComputerName());
        comp.setUserId(userDto.getUserId());
    }
}
