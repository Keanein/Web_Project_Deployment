//  Golondrina Airene M

package edu.uep.cos.attendance.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    // Endpoint that logs out the user
    @PostMapping
    public ResponseEntity<?> logout(HttpSession session) {

        // Invalidates the HTTP session to clear all user data (equivalent to session_destroy in PHP)
        session.invalidate();

        // Returns confirmation response after successful logout
        return ResponseEntity.ok("LOGGED_OUT");
    }
}
