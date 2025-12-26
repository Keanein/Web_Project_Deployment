// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository for retrieving event data for the dashboard view
@Repository
@RequiredArgsConstructor
public class EventDashboardRepository {

    // JdbcTemplate used to execute database queries
    private final JdbcTemplate jdbc;

    // Retrieves all events with details for dashboard display
    public List<Map<String, Object>> findAllEvents() {
        return jdbc.queryForList("""
            SELECT event_id, event_name, event_date, event_time, location
            FROM events
            ORDER BY event_name ASC
        """);
    }
}