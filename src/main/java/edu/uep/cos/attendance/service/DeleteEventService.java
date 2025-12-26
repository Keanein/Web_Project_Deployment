// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.DeleteEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer for deleting events
@Service
@RequiredArgsConstructor
public class DeleteEventService {

    // Repository used for event retrieval and deletion
    private final DeleteEventRepository repo;

    // Retrieves all events for deletion selection
    public List<Map<String, Object>> fetchEvents() {
        return repo.findAllEvents();
    }

    // Deletes selected events by their IDs
    public void deleteEvents(List<Integer> eventIds) {
        repo.deleteByIds(eventIds);
    }
}
