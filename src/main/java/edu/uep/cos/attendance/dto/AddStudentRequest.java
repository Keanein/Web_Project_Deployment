// Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class AddStudentRequest {

    // ID of the event where the student will be added
    private int eventId;

    // Unique student number identifier
    private String studentNumber;

    // Student full name in "Last Name, First Name" format
    private String fullName;

    // Academic year level of the student
    private String yearLevel;

    // Course ID associated with the student
    private int courseId;
}


