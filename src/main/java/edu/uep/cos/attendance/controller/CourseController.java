// Golondrina Airene M
package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.service.CourseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    // Service that handles course-related business logic
    private final CourseService courseService;

    // Retrieves course data associated with a specific event
    @GetMapping
    public ResponseEntity<?> getCoursesByEvent(
            @RequestParam int event_id,
            HttpSession session) {

        // Ensures only logged-in admins can access course data
        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Returns courses linked to the given event
        return ResponseEntity.ok(courseService.getCoursePageData(event_id));
    }
}
