//  Golondrina Airene M

package edu.uep.cos.attendance.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page")
public class LoginPageController {

    // Checks if the admin is already logged in before showing the login page
    @GetMapping("/login")
    public ResponseEntity<?> loginPage(HttpSession session) {

        // If an admin session exists, frontend can redirect to dashboard
        if (session.getAttribute("admin_id") != null) {
            return ResponseEntity.ok("ALREADY_LOGGED_IN");
        }

        // Allows frontend to display the login page
        return ResponseEntity.ok("SHOW_LOGIN");
    }

    // Returns the login error message stored in session (if any)
    @GetMapping("/login-error")
    public ResponseEntity<?> loginError(HttpSession session) {

        // Retrieves error message from session
        Object err = session.getAttribute("login_error");

        // Clears error after it is read
        session.removeAttribute("login_error");

        // Sends error message back to frontend
        return ResponseEntity.ok(err);
    }
}