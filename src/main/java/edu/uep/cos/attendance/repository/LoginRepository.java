// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository responsible for admin login data access
@Repository
@RequiredArgsConstructor
public class LoginRepository {

    // JdbcTemplate used to execute database queries
    private final JdbcTemplate jdbc;

    // Retrieves admin account details using username
    // Returns null if no matching account is found
    public Map<String, Object> findAdminByUsername(String username) {

        // Executes query and stores result in a list
        List<Map<String, Object>> res = jdbc.queryForList("""
            SELECT admin_id, username, password, full_name
            FROM admins
            WHERE username = ?
            LIMIT 1
        """, username);

        // Returns the first record or null if result is empty
        return res.isEmpty() ? null : res.get(0);
    }
}