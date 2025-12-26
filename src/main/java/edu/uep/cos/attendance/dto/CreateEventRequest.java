// Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class CreateEventRequest {

    // Name of the event to be created
    private String eventName;

    // Date when the event will occur
    private String eventDate;

    // Time when the event will take place
    private String eventTime;

    // Location where the event will be held
    private String location;
}
