package ca.mcgillcssa.cssabackend.service;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.CSSAEvent;
import ca.mcgillcssa.cssabackend.repository.CSSAEventRepository;

@Service
public class CSSAEventService {
  private final CSSAEventRepository cssaEventRepository;

  public CSSAEventService(CSSAEventRepository cssaEventRepository) {
    this.cssaEventRepository = cssaEventRepository;
  }

  public CSSAEvent createEvent(String eventName, LocalDate eventStartDate, LocalDate eventEndDate, String eventLocation,
      String eventImageUrl, String eventDescription, String eventLinkUrl) {
    CSSAEvent newEvent = new CSSAEvent(eventName, eventStartDate, eventEndDate, eventLocation, eventImageUrl,
        eventDescription, eventLinkUrl);
    return cssaEventRepository.saveEvent(newEvent);
  }
}
