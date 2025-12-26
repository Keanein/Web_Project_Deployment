//  Golondrina Airene M
package edu.uep.cos.attendance.dto;

import lombok.Data;

@Data
public class UpdateEventRequest {

    // ID of the event to be updated
    private int eventId;

    // Updated name of the event
    private String eventName;

    // Updated event date
    private String eventDate;

    // Updated event time
    private String eventTime;

    // Updated event location
    private String location;
}
