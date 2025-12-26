// GOLONDRINA AIRENE M.
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.service.GetEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class GetEventController {

    // Service used to fetch event data from the database
    private final GetEventService service;

    // Handles GET request to retrieve a specific event's details
    @GetMapping("/get")
    public ResponseEntity<?> getEvent(
            @RequestParam("event_id") int eventId) {

        // Fetches event information based on event ID
        Map<String, Object> event = service.findById(eventId);

        // Returns event details to the client
        return ResponseEntity.ok(event);
    }
}
