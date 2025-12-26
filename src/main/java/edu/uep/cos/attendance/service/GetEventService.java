// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.GetEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

// Service layer for retrieving a single event
@Service
@RequiredArgsConstructor
public class GetEventService {

    // Repository used to access event data
    private final GetEventRepository repo;

    // Retrieves event details using the event ID
    public Map<String, Object> findById(int id) {
        return repo.findById(id);
    }
}