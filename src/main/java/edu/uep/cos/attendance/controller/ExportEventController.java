// Golondrina, Airene M.

package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.service.ExportEventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// REST controller responsible for exporting event data
@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportEventController {

    // Service used to handle export logic
    private final ExportEventService service;

    // Handles export requests for single or multiple events
    @GetMapping
    public ResponseEntity<?> export(
            @RequestParam(required = false) Integer event_id,
            @RequestParam(defaultValue = "0") int all,
            HttpSession session) throws Exception {

        // Ensures only logged-in admins can export data
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Validates required parameters when exporting a single event
        if (all != 1 && event_id == null) {
            return ResponseEntity.badRequest()
                    .body("event_id is required when exporting a single event.");
        }

        // Delegates export process to the service layer
        return service.export(event_id, all == 1);
    }
}
