// Golondrina Airene M 
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class DeleteAttendanceRequest {

    // ID of the event associated with the attendance
    private int eventId;

    // ID of the student whose attendance will be deleted
    private int studentId;
}