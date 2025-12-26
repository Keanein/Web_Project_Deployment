// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// Repository responsible for retrieving and deleting events
@Repository
@RequiredArgsConstructor
public class DeleteEventRepository {

    // JdbcTemplate used for executing SQL statements
    private final JdbcTemplate jdbc;

    // Retrieves all events sorted alphabetically by event name
    public List<Map<String, Object>> findAllEvents() {
        return jdbc.queryForList("""
            SELECT event_id, event_name
            FROM events
            ORDER BY event_name ASC
        """);
    }

    // Deletes multiple events based on a list of event IDs
    public void deleteByIds(List<Integer> ids) {

        // Converts list of IDs into a comma-separated string for SQL IN clause
        String inSql = String.join(
                ",",
                ids.stream().map(String::valueOf).toList()
        );

        // Deletes events; related attendance records are removed via ON DELETE CASCADE
        jdbc.execute("DELETE FROM events WHERE event_id IN (" + inSql + ")");
    }
}