// Meaningful comments by Keanu Andrie G. Alabado

package edu.uep.cos.attendance.service;

import edu.uep.cos.attendance.repository.ExportEventRepository;
import edu.uep.cos.attendance.utility.CsvZipUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Service layer responsible for exporting event attendance data
@Service
@RequiredArgsConstructor
public class ExportEventService {

    private final ExportEventRepository repo;

    // Export ONE event only (ZIP with students + attendance)
    public ResponseEntity<?> export(Integer eventId) throws Exception {

        if (eventId == null) {
            return ResponseEntity.badRequest().body("Event ID is required");
        }

        List<Map<String, Object>> students =
                repo.findStudentsByEvent(eventId);

        List<Map<String, Object>> attendance =
                repo.findAttendanceByEvent(eventId);

        if (students.isEmpty() && attendance.isEmpty()) {
            return ResponseEntity.badRequest().body("No data found.");
        }

        byte[] zip = CsvZipUtil.createZip(students, attendance);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=event_" + eventId + ".zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(zip);
    }
}
