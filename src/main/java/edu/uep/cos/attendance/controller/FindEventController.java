// Golondrina, Airene M

package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.service.FindEventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class FindEventController {

    // Service that performs event search logic
    private final FindEventService service;

    // Searches events based on query string
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String q,
            HttpSession session) {

        // Blocks access if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Returns empty list if search query is missing or blank
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        // Returns matching events based on search keyword
        return ResponseEntity.ok(service.search(q.trim()));
    }
}
