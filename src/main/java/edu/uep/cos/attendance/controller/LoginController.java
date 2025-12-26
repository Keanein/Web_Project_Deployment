//  Golondrina Airene M.
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.dto.LoginRequest;
import edu.uep.cos.attendance.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    // Service that contains the login authentication logic
    private final LoginService loginService;

    // Handles login requests from the frontend
    @PostMapping
    public ResponseEntity<?> login(
            @RequestBody LoginRequest req,
            HttpSession session) {

        // Attempts to authenticate admin using provided credentials
        boolean success = loginService.loginAdmin(
                req.getIdentifier(),
                req.getPassword(),
                session
        );

        // If authentication succeeds, mark admin as logged in
        if (success) {
            session.setAttribute("ADMIN_LOGGED_IN", true);
            return ResponseEntity.ok("SUCCESS");
        }

        // Returns unauthorized status if login fails
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("FAILED");
    }
}