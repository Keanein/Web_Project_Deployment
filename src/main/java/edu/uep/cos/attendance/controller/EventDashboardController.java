// Golondrina Airene M
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.service.EventDashboardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class EventDashboardController {

    // Service responsible for fetching dashboard event data
    private final EventDashboardService service;

    // Fetches all events to be displayed on the admin dashboard
    @GetMapping("/events")
    public ResponseEntity<?> getEvents(HttpSession session) {

        // Denies access if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Returns list of events for dashboard display
        return ResponseEntity.ok(service.fetchEvents());
    }
}
