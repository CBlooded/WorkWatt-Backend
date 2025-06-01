package org.workwattbackend.hierarchy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HierarchyService {
    private final HierarchyRepository repository;

    public List<String> getAllOfUsersSupervisors(String userId) {
        return repository.findSupervisorsBySubordinateId(userId);
    }
}
