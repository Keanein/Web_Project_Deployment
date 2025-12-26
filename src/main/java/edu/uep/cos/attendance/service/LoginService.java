// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

// Service layer responsible for admin authentication
@Service
@RequiredArgsConstructor
public class LoginService {

    // Repository used to access admin account data
    private final AdminRepository adminRepo;

    // Password encoder used to verify hashed passwords
    private final PasswordEncoder passwordEncoder;

    // Authenticates admin credentials and creates session data
    public boolean loginAdmin(String identifier, String rawPassword, HttpSession session) {

        // Retrieves admin account by username
        Map<String, Object> admin = adminRepo.findByUsername(identifier);

        // Returns false if admin does not exist
        if (admin == null) return false;

        // Retrieves stored hashed password
        String hashedPassword = (String) admin.get("password");

        // Compares raw password with hashed password
        if (!passwordEncoder.matches(rawPassword, hashedPassword)) {
            return false;
        }

        // Login successful: store admin details in session
        session.setAttribute("admin_id", admin.get("admin_id"));
        session.setAttribute("admin_name", admin.get("full_name"));

        return true;
    }
}