// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository for retrieving event, course, and attendance data for export
@Repository
@RequiredArgsConstructor
public class ExportEventRepository {

    private final JdbcTemplate jdbc;

    // Retrieves all events sorted by date and name
    public List<Map<String, Object>> findAllEvents() {
        return jdbc.queryForList("""
            SELECT event_id, event_name, event_date
            FROM events
            ORDER BY event_date ASC, event_name ASC
        """);
    }

    // Retrieves a single event by ID
    public List<Map<String, Object>> findSingleEvent(Integer id) {
        if (id == null || id == 0) return List.of();
        return jdbc.queryForList("""
            SELECT event_id, event_name, event_date
            FROM events WHERE event_id = ?
        """, id);
    }

    // ✅ FIX: retrieve courses PER EVENT
    public List<Map<String, Object>> findCoursesByEvent(int eventId) {
        return jdbc.queryForList("""
            SELECT DISTINCT c.course_id, c.course_name
            FROM courses c
            JOIN attendance a ON a.course_id = c.course_id
            WHERE a.event_id = ?
            ORDER BY c.course_name ASC
        """, eventId);
    }

    // ✅ FIX: filter by attendance.course_id (NOT students)
    public List<Map<String, Object>> findAttendance(int eventId, int courseId) {
        return jdbc.queryForList("""
            SELECT s.student_number,
                   CONCAT(s.last_name, ', ', s.first_name) AS fullname,
                   a.status_am, a.status_pm, a.time_in_am, a.time_in_pm
            FROM attendance a
            JOIN students s ON s.student_id = a.student_id
            WHERE a.event_id = ? AND a.course_id = ?
            ORDER BY s.last_name ASC, s.first_name ASC
        """, eventId, courseId);
    }
}


// Meaningful comments by Keanu Andrie G. Alabado
//
//package edu.uep.cos.attendance.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//
//// Repository for retrieving event, course, and attendance data for export
//@Repository
//@RequiredArgsConstructor
//public class ExportEventRepository {
//
//    // JdbcTemplate used to execute SQL queries
//    private final JdbcTemplate jdbc;
//
//    // Retrieves all events sorted by date and name
//    public List<Map<String, Object>> findAllEvents() {
//        return jdbc.queryForList("""
//            SELECT event_id, event_name, event_date
//            FROM events
//            ORDER BY event_date ASC, event_name ASC
//        """);
//    }
//
//    // Retrieves a single event based on ID
//    // Returns an empty list if no valid ID is provided
//    public List<Map<String, Object>> findSingleEvent(Integer id) {
//        if (id == null || id == 0) return List.of();
//        return jdbc.queryForList("""
//            SELECT event_id, event_name, event_date
//            FROM events WHERE event_id = ?
//        """, id);
//    }
//
//    // Retrieves all courses for selection during export
//    public List<Map<String, Object>> findCourses() {
//        return jdbc.queryForList("""
//            SELECT course_id, course_name
//            FROM courses ORDER BY course_name ASC
//        """);
//    }
//
//    // Retrieves attendance records filtered by event and course
//    public List<Map<String, Object>> findAttendance(int eventId, int courseId) {
//        return jdbc.queryForList("""
//            SELECT s.student_number,
//                   CONCAT(s.last_name, ', ', s.first_name) AS fullname,
//                   a.status_am, a.status_pm, a.time_in_am, a.time_in_pm
//            FROM students s
//            JOIN attendance a ON s.student_id = a.student_id
//            WHERE a.event_id = ? AND s.course_id = ?
//            ORDER BY s.last_name ASC, s.first_name ASC
//        """, eventId, courseId);
//    }
//}