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

@Service
@RequiredArgsConstructor
public class ExportEventService {

    private final ExportEventRepository repo;

    // SINGLE CSV export (OneBillion_Rising format)
    public ResponseEntity<?> export(Integer eventId) throws Exception {

        if (eventId == null) {
            return ResponseEntity.badRequest().body("Event ID is required");
        }

        List<Map<String, Object>> data =
                repo.exportSingleCsv(eventId);

        if (data.isEmpty()) {
            return ResponseEntity.badRequest().body("No data found.");
        }

        byte[] csv = CsvZipUtil.createCsv(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=OneBillion_Rising.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }
}
