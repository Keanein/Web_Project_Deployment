// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.FindEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer for searching events
@Service
@RequiredArgsConstructor
public class FindEventService {

    // Repository used to perform event search queries
    private final FindEventRepository repo;

    // Searches events based on the provided query string
    public List<Map<String, Object>> search(String q) {
        return repo.search(q);
    }
}