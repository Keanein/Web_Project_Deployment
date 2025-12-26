// Golondrina Airene M
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.dto.*;
import edu.uep.cos.attendance.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    // Service that contains attendance business logic
    private final AttendanceService attendanceService;

    // Retrieves attendance records for a specific event and course
    @GetMapping
    public ResponseEntity<?> getAttendance(
            @RequestParam int event_id,
            @RequestParam int course_id,
            HttpSession session) {

        // Prevents access if admin is not logged in
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Returns attendance data
        return ResponseEntity.ok(
                attendanceService.fetchAttendance(event_id, course_id)
        );
    }

    // Adds a new student to the attendance list
    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody AddStudentRequest req) {

        // Delegates student creation and attendance linking to service
        attendanceService.addStudent(req);
        return ResponseEntity.ok("Student added");
    }

    // Deletes a student's attendance record
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody DeleteAttendanceRequest req) {

        // Removes attendance (and possibly student record)
        attendanceService.deleteAttendance(req);
        return ResponseEntity.ok("Deleted");
    }

    // Updates a student's name
    @PostMapping("/edit-name")
    public ResponseEntity<?> editName(@RequestBody EditNameRequest req) {

        // Updates student name in the database
        attendanceService.editStudentName(req);
        return ResponseEntity.ok("Name updated");
    }

    // Updates attendance status (AM or PM)
    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateAttendanceRequest req) {

        // Updates attendance status for the given period
        attendanceService.updateStatus(
                req.getEventId(),
                req.getStudentId(),
                req.getPeriod(),
                req.getStatus()
        );
        return ResponseEntity.ok("Status updated");
    }
}