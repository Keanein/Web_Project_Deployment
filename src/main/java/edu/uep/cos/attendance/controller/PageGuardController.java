// Golondrina Airene M

package edu.uep.cos.attendance.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST controller for guarding page access
@RequestMapping("/page") // Base URL for page access checks
public class PageGuardController {

    @GetMapping("/create-event") // Endpoint to check access for Create Event page
    public ResponseEntity<?> createEventPage(HttpSession session) {

        // Check if admin is logged in (same logic as PHP session check)
        // Equivalent to: if (!isset($_SESSION["admin_id"]))
        if (session.getAttribute("admin_id") == null) {
            // Block access if admin is not logged in
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Allow access if admin session exists
        return ResponseEntity.ok("ALLOWED");
    }
}