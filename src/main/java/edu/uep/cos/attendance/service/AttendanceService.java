// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.dto.*;
import edu.uep.cos.attendance.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

// AttendanceService.java

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repo;

    public Map<String, Object> fetchAttendance(int eventId, int courseId) {
        return repo.getAttendance(eventId, courseId);
    }

    public void addStudent(AddStudentRequest req) {
        repo.addStudent(req);
    }

    public void deleteAttendance(DeleteAttendanceRequest req) {
        repo.deleteAttendance(req);
    }

    public void editStudentName(EditNameRequest req) {
        repo.editStudentName(req);
    }

    public void updateStatus(int eventId, int studentId, String period, String status) {
        if ("AM".equals(period)) {
            repo.updateAM(eventId, studentId, status);
        } else {
            repo.updatePM(eventId, studentId, status);
        }
    }
}

