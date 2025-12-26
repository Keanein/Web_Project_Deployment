// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import edu.uep.cos.attendance.dto.AddStudentRequest;
import edu.uep.cos.attendance.dto.DeleteAttendanceRequest;
import edu.uep.cos.attendance.dto.EditNameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {

    private final JdbcTemplate jdbc;

    // ✅ FIXED: course_id comes from students table
    public Map<String, Object> getAttendance(int eventId, int courseId) {

        Map<String, Object> result = new HashMap<>();

        String eventName = jdbc.queryForObject(
                "SELECT event_name FROM events WHERE event_id = ?",
                String.class,
                eventId
        );

        List<Map<String, Object>> students = jdbc.queryForList("""
            SELECT 
                s.student_id,
                s.student_number,
                CONCAT(s.last_name, ', ', s.first_name) AS fullname,
                s.year_level,
                a.status_am,
                a.status_pm
            FROM attendance a
            JOIN students s ON s.student_id = a.student_id
            WHERE a.event_id = ? AND s.course_id = ?
            ORDER BY s.last_name, s.first_name
        """, eventId, courseId);

        result.put("event_name", eventName);
        result.put("students", students);
        return result;
    }

    // ✅ FIXED: insert student WITH course_id
    public void addStudent(AddStudentRequest req) {

        String[] parts = req.getFullName().split(",", 2);
        String lastName = parts[0].trim();
        String firstName = parts.length > 1 ? parts[1].trim() : "";

        Integer studentId = jdbc.query(
                "SELECT student_id FROM students WHERE student_number = ?",
                rs -> rs.next() ? rs.getInt(1) : null,
                req.getStudentNumber()
        );

        if (studentId == null) {
            jdbc.update("""
                INSERT INTO students (student_number, first_name, last_name, year_level, course_id)
                VALUES (?, ?, ?, ?, ?)
            """,
                    req.getStudentNumber(),
                    firstName,
                    lastName,
                    req.getYearLevel(),
                    req.getCourseId()
            );

            studentId = jdbc.queryForObject(
                    "SELECT student_id FROM students WHERE student_number = ?",
                    Integer.class,
                    req.getStudentNumber()
            );
        }

        // attendance table has NO course_id → correct
        jdbc.update("""
            INSERT INTO attendance (event_id, student_id)
            VALUES (?, ?)
        """, req.getEventId(), studentId);
    }

    public void deleteAttendance(DeleteAttendanceRequest req) {
        jdbc.update("""
            DELETE FROM attendance
            WHERE event_id = ? AND student_id = ?
        """, req.getEventId(), req.getStudentId());
    }

    public void editStudentName(EditNameRequest req) {

        String[] parts = req.getFullName().split(",", 2);
        String lastName = parts[0].trim();
        String firstName = parts.length > 1 ? parts[1].trim() : "";

        jdbc.update("""
            UPDATE students
            SET first_name = ?, last_name = ?
            WHERE student_id = ?
        """, firstName, lastName, req.getStudentId());
    }

    public void updateAM(int eventId, int studentId, String status) {
        jdbc.update("""
            UPDATE attendance
            SET status_am = ?
            WHERE event_id = ? AND student_id = ?
        """, status, eventId, studentId);
    }

    public void updatePM(int eventId, int studentId, String status) {
        jdbc.update("""
            UPDATE attendance
            SET status_pm = ?
            WHERE event_id = ? AND student_id = ?
        """, status, eventId, studentId);
    }
}

