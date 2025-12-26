// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.dto.CreateAdminRequest;
import edu.uep.cos.attendance.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Service layer that handles admin account creation logic
@Service
@RequiredArgsConstructor
public class AdminService {

    // Repository for admin database operations
    private final AdminRepository adminRepo;

    // Password encoder used to hash admin passwords
    private final PasswordEncoder passwordEncoder;

    // Creates a new admin account
    public String createAdmin(CreateAdminRequest req) {

        // Removes extra spaces from input fields
        String username = req.getUsername().trim();
        String password = req.getPassword().trim();
        String confirm  = req.getConfirm().trim();

        // Validates required fields
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            return "All fields are required.";
        }

        // Checks if passwords match
        if (!password.equals(confirm)) {
            return "Passwords do not match.";
        }

        // Checks if username already exists
        if (adminRepo.existsByUsername(username)) {
            return "Username already exists. Try another.";
        }

        // Encrypts the password before saving
        String hashedPassword = passwordEncoder.encode(password);

        // Saves admin account to database
        boolean saved = adminRepo.insertAdmin(
                username,
                hashedPassword,
                "Administrator"
        );

        // Returns result message
        return saved ? "SUCCESS" : "Error creating account. Please try again.";
    }
}
