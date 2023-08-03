package ca.mcgillcssa.cssabackend.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    if (eventStartDate.isAfter(eventEndDate)) {
      throw new IllegalArgumentException("eventStartDate must be after than eventEndDate");
    }

    try {
      if (eventImageUrl != null && !eventImageUrl.isEmpty()) {
        UrlChecker urlChecker = UrlChecker.isValidUrl(eventImageUrl);
        if (!urlChecker.isValid()) {
          throw new IllegalArgumentException("event image url connection failed");
        }
        eventImageUrl = urlChecker.getUrl();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("event image url format error");
    }

    try {
      if (eventLinkUrl != null && !eventLinkUrl.isEmpty()) {
        UrlChecker urlChecker = UrlChecker.isValidUrl(eventLinkUrl);
        if (!urlChecker.isValid()) {
          throw new IllegalArgumentException("event link url connection failed");
        }
        eventLinkUrl = urlChecker.getUrl();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("event link url format error");
    }

    return cssaEventRepository.createEvent(newEvent);
  }

  public Optional<CSSAEvent> findEventByName(String name) {
    return cssaEventRepository.findEventByName(name);
  }

  public Optional<List<CSSAEvent>> findAllEvents() {
    return cssaEventRepository.findAllEvents();
  }

  public boolean deleteEventByName(String name) {
    return cssaEventRepository.deleteByName(name);
  }

  public boolean deleteAll() {
    return cssaEventRepository.deleteAll();
  }

  public List<CSSAEvent> getAllEvents() {
    return cssaEventRepository.findAll();
  }

  public boolean updateEventByName(String eventName, String newEventName, LocalDate eventStartDate,
      LocalDate eventEndDate, String eventLocation, String eventImageUrl, String eventDescription,
      String eventLinkUrl) throws IOException {

    Optional<CSSAEvent> optionalEvent = cssaEventRepository.findEventByName(eventName);

    if (optionalEvent.isPresent()) {
      CSSAEvent event = optionalEvent.get();
      // Check if any fields have changed
      boolean hasChanges = false;
      if (newEventName != null && !newEventName.equals(event.getEventName())) {
        event.setEventName(newEventName);
        hasChanges = true;
      }
      if (eventStartDate != null && !eventStartDate.equals(event.getEventStartDate())) {
        event.setEventStartDate(eventStartDate);
        hasChanges = true;
      }
      if (eventEndDate != null && !eventEndDate.equals(event.getEventEndDate())) {
        event.setEventEndDate(eventEndDate);
        hasChanges = true;
      }
      if (eventLocation != null && !eventLocation.equals(event.getEventLocation())) {
        event.setEventLocation(eventLocation);
        hasChanges = true;
      }
      if (eventImageUrl != null && !eventImageUrl.equals(event.getEventImageUrl())) {
        event.setEventImageUrl(eventImageUrl);
        hasChanges = true;
      }
      if (eventDescription != null && !eventDescription.equals(event.getEventDescription())) {
        event.setEventDescription(eventDescription);
        hasChanges = true;
      }
      if (eventLinkUrl != null && !eventLinkUrl.equals(event.getEventLinkUrl())) {
        event.setEventLinkUrl(eventLinkUrl);
        hasChanges = true;
      }

      if (!hasChanges) {
        throw new IllegalArgumentException("Nothing to be changed");
      }

      try {
        if (eventImageUrl != null && !eventImageUrl.isEmpty()) {
          UrlChecker urlChecker = UrlChecker.isValidUrl(eventImageUrl);
          if (!urlChecker.isValid()) {
            throw new IllegalArgumentException("event image url connection failed");
          }
          eventImageUrl = urlChecker.getUrl();
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("event image url format error");
      }

      try {
        if (eventLinkUrl != null && !eventLinkUrl.isEmpty()) {
          UrlChecker urlChecker = UrlChecker.isValidUrl(eventLinkUrl);
          if (!urlChecker.isValid()) {
            throw new IllegalArgumentException("event link url connection failed");
          }
          eventLinkUrl = urlChecker.getUrl();
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("event link url format error");
      }

      if (eventStartDate != null && eventStartDate.isAfter(eventEndDate)) {
        throw new IllegalArgumentException("eventStartDate must be after than eventEndDate");
      }

      cssaEventRepository.deleteByName(eventName);
      cssaEventRepository.save(event);
      return true;
    } else {
      throw new IllegalArgumentException("Event does not exist with name: " + eventName);
    }
  }

  public List<CSSAEvent> findUpcomingEvents(int limit) {
    return cssaEventRepository.findUpcomingEvents(limit);
  }

  public boolean deleteAllEvents() {
    cssaEventRepository.deleteAll();
    return true;
  }

}
