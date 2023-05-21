package ca.mcgillcssa.cssabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ca.mcgillcssa.cssabackend.model.CSSAEvent;

@Data
@AllArgsConstructor

public class CSSAEventDTO {
  private String id;
  private String eventName;
  private LocalDate eventStartDate;
  private LocalDate eventEndDate;
  private String eventLocation;
  private String eventImageUrl;
  private String eventDescription;
  private String eventLinkUrl;

  public CSSAEventDTO(CSSAEvent cssaEvent) {
    this.id = cssaEvent.getId();
    this.eventName = cssaEvent.getEventName();
    this.eventStartDate = cssaEvent.getEventStartDate();
    this.eventEndDate = cssaEvent.getEventEndDate();
    this.eventLocation = cssaEvent.getEventLocation();
    this.eventImageUrl = cssaEvent.getEventImageUrl();
    this.eventDescription = cssaEvent.getEventDescription();
    this.eventLinkUrl = cssaEvent.getEventLinkUrl();

  }
}
