// Golondrina Airene M
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.dto.CreateAdminRequest;
import edu.uep.cos.attendance.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    // Service that handles admin creation logic
    private final AdminService adminService;

    // Creates a new admin account
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminRequest req) {

        // Calls service to validate and create admin
        String result = adminService.createAdmin(req);

        // Returns success response if admin is created
        if (result.equals("SUCCESS")) {
            return ResponseEntity.ok("REGISTERED");
        }

        // Returns error message if creation fails
        return ResponseEntity.badRequest().body(result);
    }
}