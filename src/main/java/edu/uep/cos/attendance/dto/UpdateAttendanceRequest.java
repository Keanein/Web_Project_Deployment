// Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class UpdateAttendanceRequest {

    // ID of the student whose attendance will be updated
    private int studentId;

    // ID of the event where attendance is recorded
    private int eventId;

    // Time period of attendance (AM or PM)
    private String period;

    // Attendance status (e.g., Present or Absent)
    private String status;
}