package ca.mcgillcssa.cssabackend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.CSSAEvent;
import ca.mcgillcssa.cssabackend.repository.CSSAEventRepository;
import ca.mcgillcssa.cssabackend.util.UrlChecker;

@Service
public class CSSAEventService {
  private final CSSAEventRepository cssaEventRepository;

  public CSSAEventService(CSSAEventRepository cssaEventRepository) {
    this.cssaEventRepository = cssaEventRepository;
  }

  public CSSAEvent createEvent(String eventName, LocalDate eventStartDate, LocalDate eventEndDate, String eventLocation,
      String eventImageUrl, String eventDescription, String eventLinkUrl) throws IOException {
    CSSAEvent newEvent = new CSSAEvent(eventName, eventStartDate, eventEndDate, eventLocation, eventImageUrl,
        eventDescription, eventLinkUrl);

    if (eventName == null || eventStartDate == null || eventEndDate == null || eventLocation == null
        || eventImageUrl == null || eventDescription == null || eventLinkUrl == null) {
      throw new IllegalArgumentException(
          "Missing information: eventName, eventStartDate, eventEndDate, eventLocation, eventImageUrl, eventDescription and eventLinkUrl is required.");
    }

    // No duplication tests

    if (!UrlChecker.isValidUrl(eventImageUrl)) {
      throw new IllegalArgumentException("CSSAEvent image url is not valid");
    }

    if (!UrlChecker.isValidUrl(eventLinkUrl)) {
      throw new IllegalArgumentException("CSSAEvent link url is not valid");
    }

    return cssaEventRepository.createEvent(newEvent);
  }

  public Optional<CSSAEvent> findEventById(String id) {
    return cssaEventRepository.findEventById(id);
  }

  public Optional<CSSAEvent> findEventByName(String name) {
    return cssaEventRepository.findEventByName(name);
  }

  public Optional<List<CSSAEvent>> findAllEvents() {
    return cssaEventRepository.findAllEvents();
  }

  public boolean deletEventById(String id) {
    return cssaEventRepository.deleteById(id);
  }

  public boolean deletEventByName(String name) {
    return cssaEventRepository.deleteByName(name);
  }

  public boolean updateEvent(String id, String eventName, LocalDate eventStartDate, LocalDate eventEndDate,
      String eventLocation, String eventImageUrl, String eventDescription, String eventLinkUrl) throws IOException {
    // object availavility check
    if (!cssaEventRepository.findEventById(id).isPresent()) {
      throw new IllegalArgumentException("CSSAEvent does not exist");
    }

    // If all fields are null, throw exception
    if ((eventName == null || eventName.isEmpty())
        && (eventStartDate == null || eventStartDate.toString().isEmpty())
        && (eventEndDate == null || eventEndDate.toString().isEmpty())
        && (eventLocation == null || eventLocation.isEmpty())
        && (eventImageUrl == null || eventImageUrl.isEmpty())
        && (eventDescription == null || eventDescription.isEmpty())
        && (eventLinkUrl == null || eventLinkUrl.isEmpty())) {
      throw new IllegalArgumentException("Nothing to be changed");
    }

    if (eventImageUrl != null && !eventImageUrl.isEmpty() && !UrlChecker.isValidUrl(eventImageUrl)) {
      throw new IllegalArgumentException("Event image url is not valid");
    }

    if (eventLinkUrl != null && !eventLinkUrl.isEmpty() && !UrlChecker.isValidUrl(eventLinkUrl)) {
      throw new IllegalArgumentException("Event link website url is not valid");
    }

    return cssaEventRepository.updateCSSAEvent(id, eventName, eventStartDate, eventEndDate,
        eventLocation, eventImageUrl, eventDescription, eventLinkUrl);
  }

  public List<CSSAEvent> findUpcomingEvents(int limit) {
    return cssaEventRepository.findUpcomingEvents(limit);
  }

}
