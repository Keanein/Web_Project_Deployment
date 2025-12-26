// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.EventDashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer for retrieving event data for the dashboard
@Service
@RequiredArgsConstructor
public class EventDashboardService {

    // Repository used to access event records
    private final EventDashboardRepository repo;

    // Fetches all events to be displayed on the dashboard
    public List<Map<String, Object>> fetchEvents() {
        return repo.findAllEvents();
    }
}
