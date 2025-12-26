// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.dto.*;
import edu.uep.cos.attendance.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

// Service layer that handles attendance-related business logic
@Service
@RequiredArgsConstructor
public class AttendanceService {

    // Repository used for attendance database operations
    private final AttendanceRepository repo;

    // Retrieves attendance data for a specific event and course
    public Map<String, Object> fetchAttendance(int eventId, int courseId) {
        return repo.getAttendance(eventId, courseId);
    }

    // Adds a student and links them to attendance
    public void addStudent(AddStudentRequest req) {
        repo.addStudent(req);
    }

    // Removes a student's attendance record
    public void deleteAttendance(DeleteAttendanceRequest req) {
        repo.deleteAttendance(req);
    }

    // Updates a student's name
    public void editStudentName(EditNameRequest req) {
        repo.editStudentName(req);
    }

    // Updates attendance status based on AM or PM period
    public void updateStatus(int eventId, int studentId, String period, String status) {
        if ("AM".equals(period)) {
            repo.updateAM(eventId, studentId, status);
        } else {
            repo.updatePM(eventId, studentId, status);
        }
    }
}