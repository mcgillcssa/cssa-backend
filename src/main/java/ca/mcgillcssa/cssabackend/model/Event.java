package ca.mcgillcssa.cssabackend.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document(collection = "events")
@Data
@ToString

public class Event {
  @Id
  private String id;
  private String eventName;
  private LocalDate eventStartDate;
  private LocalDate eventEndDate;
  private String eventLocation;
  private String eventImageUrl;
  private String eventDescription;
  private String eventLinkUrl;

  public Event(String eventName, LocalDate eventStartDate, LocalDate eventEndDate, String eventLocation,
      String eventImageUrl, String eventDescription, String eventLinkUrl) {
    this.eventName = eventName;
    this.eventStartDate = eventStartDate;
    this.eventEndDate = eventEndDate;
    this.eventLocation = eventLocation;
    this.eventImageUrl = eventImageUrl;
    this.eventDescription = eventDescription;
    this.eventLinkUrl = eventLinkUrl;
  }
}
