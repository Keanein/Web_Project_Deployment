// Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class EditNameRequest {

    // ID of the student whose name will be updated
    private int studentId;

    // New full name in "Last Name, First Name" format
    private String fullName;
}




