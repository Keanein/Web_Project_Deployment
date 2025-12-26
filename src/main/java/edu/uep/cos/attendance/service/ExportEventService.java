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

    // Exports attendance data as CSV (single event) or ZIP (all events)
    public ResponseEntity<?> export(Integer eventId, boolean exportAll) throws Exception {

        // Fetch events
        List<Map<String, Object>> events =
                exportAll ? repo.findAllEvents() : repo.findSingleEvent(eventId);

        if (events.isEmpty()) {
            return ResponseEntity.badRequest().body("No events found.");
        }

        // EXPORT ALL → ZIP
        if (exportAll) {
            byte[] zip = CsvZipUtil.buildZip(events, repo);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=All_Events_" + System.currentTimeMillis() + ".zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zip);
        }

        // EXPORT ONE → CSV
        byte[] csv = CsvZipUtil.buildCsv(events.get(0), repo);
        String name = CsvZipUtil.fileName(events.get(0));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + name)
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }
}
