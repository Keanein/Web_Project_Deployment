// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.dto.UpdateEventRequest;
import edu.uep.cos.attendance.repository.UpdateEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for updating event information
@Service
@RequiredArgsConstructor
public class UpdateEventService {

    // Repository used to perform event update operations
    private final UpdateEventRepository repo;

    // Updates event details using data from the request object
    public void update(UpdateEventRequest req) {
        repo.updateEvent(
                req.getEventId(),
                req.getEventName(),
                req.getEventDate(),
                req.getEventTime(),
                req.getLocation()
        );
    }
}
