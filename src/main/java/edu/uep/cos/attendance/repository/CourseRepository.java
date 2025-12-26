// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository for handling course-related database operations
@Repository
@RequiredArgsConstructor
public class CourseRepository {

    // JdbcTemplate used to run SQL queries
    private final JdbcTemplate jdbc;

    // Retrieves all courses sorted alphabetically by course name
    public List<Map<String, Object>> findAllCourses() {
        return jdbc.queryForList("""
            SELECT course_id, course_name
            FROM courses
            ORDER BY course_name ASC
        """);
    }
}
