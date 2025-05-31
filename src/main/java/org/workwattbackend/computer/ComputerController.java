package org.workwattbackend.computer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.workwattbackend.computer.dto.NewComputerDto;
import org.workwattbackend.computer.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ComputerController {
    private final ComputerService computerService;

    @PostMapping("/addComputers")
    public ResponseEntity<Void> addComputers(@RequestBody NewComputerDto newComputer) {
        computerService.addComputers(newComputer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFreeComputers")
    public ResponseEntity<List<NewComputerDto>> getNotAssignedComputers(){
        return ResponseEntity.ok(computerService.getFreeComputers());
    }

    @PostMapping("/assignUser")
    public ResponseEntity<Void> assignUser(@RequestBody UserDto userDto){
        computerService.assignUser(userDto);
        return ResponseEntity.ok().build();
    }
}
