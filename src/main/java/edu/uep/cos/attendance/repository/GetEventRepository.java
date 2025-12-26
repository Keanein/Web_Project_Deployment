// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository for retrieving a single event by ID
@Repository
@RequiredArgsConstructor
public class GetEventRepository {

    // JdbcTemplate used to execute SQL queries
    private final JdbcTemplate jdbc;

    // Retrieves event details using the event ID
    // Returns an empty map if the event does not exist
    public Map<String, Object> findById(int id) {

        // Executes query and stores result in a list
        List<Map<String, Object>> res = jdbc.queryForList(
                "SELECT * FROM events WHERE event_id = ?", id
        );

        // Returns first result or empty map if no data is found
        return res.isEmpty() ? Map.of() : res.get(0);
    }
}