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

// Repository layer responsible for attendance-related database operations
@Repository
@RequiredArgsConstructor
public class AttendanceRepository {

    // Used to execute SQL queries
    private final JdbcTemplate jdbc;

    // Retrieves attendance list filtered by event and course
    public Map<String, Object> getAttendance(int eventId, int courseId) {

        // Container for event name and student list
        Map<String, Object> result = new HashMap<>();

        // Fetches the event name using event ID
        String eventName = jdbc.queryForObject(
                "SELECT event_name FROM events WHERE event_id = ?",
                String.class,
                eventId
        );

        // Fetches students and their attendance status for AM and PM
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
            WHERE a.event_id = ? AND a.course_id = ?
        """, eventId, courseId);

        // Stores results in a map
        result.put("event_name", eventName);
        result.put("students", students);
        return result;
    }

    // Adds a student and links them to an event and course
    public void addStudent(AddStudentRequest req) {

        // Splits full name into last name and first name
        String[] parts = req.getFullName().split(",", 2);
        String lastName = parts[0].trim();
        String firstName = parts.length > 1 ? parts[1].trim() : "";

        // Checks if student already exists by student number
        Integer studentId = jdbc.query(
                "SELECT student_id FROM students WHERE student_number = ?",
                rs -> rs.next() ? rs.getInt(1) : null,
                req.getStudentNumber()
        );

        // If student does not exist, insert new record
        if (studentId == null) {
            jdbc.update("""
                INSERT INTO students (student_number, first_name, last_name, year_level)
                VALUES (?, ?, ?, ?)
            """, req.getStudentNumber(), firstName, lastName, req.getYearLevel());

            // Retrieve newly created student ID
            studentId = jdbc.queryForObject(
                    "SELECT student_id FROM students WHERE student_number = ?",
                    Integer.class,
                    req.getStudentNumber()
            );
        }

        // Inserts attendance record for the student
        jdbc.update("""
            INSERT INTO attendance (event_id, course_id, student_id)
            VALUES (?, ?, ?)
        """, req.getEventId(), req.getCourseId(), studentId);
    }

    // Deletes a student's attendance record
    public void deleteAttendance(DeleteAttendanceRequest req) {

        // Removes attendance record for the given event and student
        jdbc.update("""
            DELETE FROM attendance
            WHERE event_id = ? AND student_id = ?
        """, req.getEventId(), req.getStudentId());

        // Checks if student still has remaining attendance records
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM attendance WHERE student_id = ?",
                Integer.class,
                req.getStudentId()
        );

        // Deletes student record if no attendance records remain
        if (count != null && count == 0) {
            jdbc.update(
                    "DELETE FROM students WHERE student_id = ?",
                    req.getStudentId()
            );
        }
    }

    // Updates a student's first and last name
    public void editStudentName(EditNameRequest req) {

        // Splits updated full name
        String[] parts = req.getFullName().split(",", 2);
        String lastName = parts[0].trim();
        String firstName = parts.length > 1 ? parts[1].trim() : "";

        // Updates student name in database
        jdbc.update("""
            UPDATE students
            SET first_name = ?, last_name = ?
            WHERE student_id = ?
        """, firstName, lastName, req.getStudentId());
    }

    // Updates AM attendance status
    public void updateAM(int eventId, int studentId, String status) {
        jdbc.update("""
            UPDATE attendance
            SET status_am = ?
            WHERE event_id = ? AND student_id = ?
        """, status, eventId, studentId);
    }

    // Updates PM attendance status
    public void updatePM(int eventId, int studentId, String status) {
        jdbc.update("""
            UPDATE attendance
            SET status_pm = ?
            WHERE event_id = ? AND student_id = ?
        """, status, eventId, studentId);
    }
}
