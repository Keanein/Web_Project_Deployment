// Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;
import java.util.List;

@Data
public class DeleteEventRequest {

    // List of event IDs selected for deletion
    private List<Integer> eventIds;
}
