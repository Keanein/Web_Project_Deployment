// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.CourseRepository;
import edu.uep.cos.attendance.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// Service layer for preparing course page data
@Service
@RequiredArgsConstructor
public class CourseService {

    // Repository for course data
    private final CourseRepository courseRepo;

    // Repository for event data
    private final EventRepository eventRepo;

    // Gathers event details and available courses for the course page
    public Map<String, Object> getCoursePageData(int eventId) {

        // Container for page data
        Map<String, Object> data = new HashMap<>();

        // Fetches event details using event ID
        Map<String, Object> event = eventRepo.findEventById(eventId);

        // Provides default values if event is not found
        data.put("event", event != null ? event : Map.of(
                "event_name", "Unknown Event",
                "event_date", null,
                "event_time", null,
                "location", null
        ));

        // Fetches all available courses
        data.put("courses", courseRepo.findAllCourses());

        return data;
    }
}