//  Golondrina, Airene M
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.dto.DeleteEventRequest;
import edu.uep.cos.attendance.service.DeleteEventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class DeleteEventController {

    // Service that handles event deletion logic
    private final DeleteEventService deleteEventService;

    // Retrieves all events for deletion selection
    @GetMapping("/all")
    public ResponseEntity<?> getEvents(HttpSession session) {

        // Denies access if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Returns list of events
        return ResponseEntity.ok(deleteEventService.fetchEvents());
    }

    // Deletes selected events
    @PostMapping("/delete")
    public ResponseEntity<?> deleteEvents(
            @RequestBody DeleteEventRequest req,
            HttpSession session) {

        // Denies access if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Validates that at least one event is selected
        if (req.getEventIds() == null || req.getEventIds().isEmpty()) {
            return ResponseEntity.badRequest().body("Please select at least one event.");
        }

        // Calls service to delete selected events
        deleteEventService.deleteEvents(req.getEventIds());

        // Confirms successful deletion
        return ResponseEntity.ok("Selected events successfully deleted.");
    }
}
