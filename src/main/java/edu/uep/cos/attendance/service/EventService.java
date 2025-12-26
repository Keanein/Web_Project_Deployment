// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.dto.CreateEventRequest;
import edu.uep.cos.attendance.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Service layer for handling event creation and updates
@Service
@RequiredArgsConstructor
public class EventService {

    // Repository used for event database operations
    private final EventRepository eventRepo;

    // Creates a new event using data from the request and admin ID
    public void createEvent(CreateEventRequest req, int adminId) {
        eventRepo.insertEvent(
                req.getEventName(),
                req.getEventDate(),
                req.getEventTime(),
                req.getLocation(),
                adminId
        );
    }

    // Updates an existing event's details
    public void updateEvent(
            int eventId,
            String eventName,
            String eventDate,
            String eventTime,
            String location
    ) {
        eventRepo.updateEvent(
                eventId,
                eventName,
                eventDate,
                eventTime,
                location
        );
    }
}
