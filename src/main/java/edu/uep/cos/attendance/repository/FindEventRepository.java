// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository for searching events based on keywords
@Repository
@RequiredArgsConstructor
public class FindEventRepository {

    // JdbcTemplate used to execute database queries
    private final JdbcTemplate jdbc;

    // Searches events by name, date, or location
    public List<Map<String, Object>> search(String q) {

        // Adds wildcard characters for partial matching
        String like = "%" + q + "%";

        return jdbc.queryForList("""
            SELECT e.event_id, e.event_name, e.event_date, e.event_time, e.location,
                   a.full_name AS creator
            FROM events e
            LEFT JOIN admins a ON e.created_by = a.admin_id
            WHERE e.event_name LIKE ?
               OR e.event_date LIKE ?
               OR e.location LIKE ?
            ORDER BY e.event_date DESC
        """, like, like, like);
    }
}

