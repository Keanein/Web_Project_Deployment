// Golondrina, Airene M.

package edu.uep.cos.attendance.controller;

import edu.uep.cos.attendance.service.ExportEventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportEventController {

    private final ExportEventService service;

    @GetMapping
    public ResponseEntity<?> export(
            @RequestParam Integer event_id,
            HttpSession session) throws Exception {

        if (session.getAttribute("admin_id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return service.export(event_id);
    }
}


