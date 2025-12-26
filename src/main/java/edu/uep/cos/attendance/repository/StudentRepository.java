// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Repository for handling student-related database operations
@Repository
@RequiredArgsConstructor
public class StudentRepository {

    // JdbcTemplate used to execute SQL queries
    private final JdbcTemplate jdbc;

    // Checks if a student number already exists in the database
    public boolean existsByStudentNumber(String studentNumber) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM students WHERE student_number = ?",
                Integer.class,
                studentNumber
        );

        // Returns true if at least one matching record exists
        return count != null && count > 0;
    }

    // Inserts a new student record into the students table
    public boolean insertStudent(String studentNumber, String first,
                                 String last, int courseId,
                                 int yearLevel, String password) {

        // Executes insert query and returns true if insert was successful
        return jdbc.update("""
            INSERT INTO students
            (student_number, first_name, last_name, course_id, year_level, password)
            VALUES (?, ?, ?, ?, ?, ?)
        """, studentNumber, first, last, courseId, yearLevel, password) > 0;
    }

    // Updates the first and last name of an existing student
    public void updateName(String firstName, String lastName, int studentId) {
        jdbc.update("""
            UPDATE students
            SET first_name = ?, last_name = ?
            WHERE student_id = ?
        """, firstName, lastName, studentId);
    }
}