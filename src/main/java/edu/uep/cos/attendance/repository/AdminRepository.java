// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

// Marks this class as a Repository for database operations
@Repository
// Automatically creates a constructor for final fields
@RequiredArgsConstructor
public class AdminRepository {

    // Is used to execute SQL queries
    private final JdbcTemplate jdbc;

    // Checks if an admin username already exists in the database
    public boolean existsByUsername(String username) {

        // Counts how many records match the given username
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM admins WHERE username = ?",
                Integer.class,
                username
        );

        // Returns true if at least one record exists
        return count != null && count > 0;
    }

    // Retrieves admin details based on username
    public Map<String, Object> findByUsername(String username) {
        // Executes query and returns the result as a key-value map
        return jdbc.queryForMap(
                "SELECT * FROM admins WHERE username = ?",
                username
        );
    }

    // Inserts a new admin record into the database
    public boolean insertAdmin(String username, String password, String fullName) {

        // Executes INSERT statement and returns true if a row was added
        return jdbc.update("""
            INSERT INTO admins (username, password, full_name)
            VALUES (?, ?, ?)
        """, username, password, fullName) > 0;
    }
}
