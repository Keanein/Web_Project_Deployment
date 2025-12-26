// Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class LoginRequest {

    // Username or identifier used for login
    private String identifier;

    // Password provided for authentication
    private String password;
}
