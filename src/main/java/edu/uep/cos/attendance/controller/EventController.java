//  Golondrina Airene M
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.dto.CreateEventRequest;
import edu.uep.cos.attendance.dto.UpdateEventRequest;
import edu.uep.cos.attendance.service.EventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    // Service that contains business logic for event operations
    private final EventService eventService;

    // Creates a new event
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(
            @RequestBody CreateEventRequest req,
            HttpSession session) {

        // Blocks request if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Calls service to save event with the current admin as creator
        eventService.createEvent(req, (int) session.getAttribute("admin_id"));
        return ResponseEntity.ok("CREATED");
    }

    // Updates an existing event
    @PostMapping("/update")
    public ResponseEntity<?> updateEvent(
            @RequestBody UpdateEventRequest req,
            HttpSession session) {

        // Blocks request if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Calls service to update event details
        eventService.updateEvent(
                req.getEventId(),
                req.getEventName(),
                req.getEventDate(),
                req.getEventTime(),
                req.getLocation()
        );

        // Confirms successful update
        return ResponseEntity.ok("UPDATED");
    }
}
