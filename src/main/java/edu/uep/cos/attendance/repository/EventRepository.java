// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

// Repository responsible for event-related database operations
@Repository
@RequiredArgsConstructor
public class EventRepository {

    // JdbcTemplate used to execute SQL queries
    private final JdbcTemplate jdbc;

    // Inserts a new event record into the events table
    public void insertEvent(String name, String date, String time,
                            String location, int createdBy) {

        jdbc.update("""
            INSERT INTO events (event_name, event_date, event_time, location, created_by)
            VALUES (?, ?, ?, ?, ?)
        """, name, date, time, location, createdBy);
    }

    // Retrieves event details based on event ID
    public Map<String, Object> findEventById(int eventId) {
        return jdbc.queryForMap("""
            SELECT event_name, event_date, event_time, location
            FROM events
            WHERE event_id = ?
        """, eventId);
    }

    // Updates existing event information
    public void updateEvent(int eventId,
                            String name,
                            String date,
                            String time,
                            String location) {

        jdbc.update("""
            UPDATE events
            SET event_name = ?,
                event_date = ?,
                event_time = ?,
                location = ?
            WHERE event_id = ?
        """, name, date, time, location, eventId);
    }
}
