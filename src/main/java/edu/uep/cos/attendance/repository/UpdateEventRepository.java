// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Repository responsible for updating event information
@Repository
@RequiredArgsConstructor
public class UpdateEventRepository {

    // JdbcTemplate used to execute SQL update statements
    private final JdbcTemplate jdbc;

    // Updates event details based on event ID
    public void updateEvent(int id, String name, String date,
                            String time, String location) {

        jdbc.update("""
            UPDATE events
            SET event_name = ?, event_date = ?, event_time = ?, location = ?
            WHERE event_id = ?
        """, name, date, time, location, id);
    }
}
