// Golondrina Airene M 
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class CreateAdminRequest {

    // Username for the new admin account
    private String username;

    // Password for the admin account
    private String password;

    // Confirmation password to validate matching input
    private String confirm;
}