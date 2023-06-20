package ca.mcgillcssa.cssabackend.controller;

import ca.mcgillcssa.cssabackend.dto.CSSAEventDTO;
import ca.mcgillcssa.cssabackend.model.CSSAEvent;
import ca.mcgillcssa.cssabackend.service.CSSAEventService;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.ToString;

@RestController
@RequestMapping("/events")
public class CSSAEventController {
  private final CSSAEventService cssaEventService;

  public CSSAEventController(CSSAEventService cssaEventService) {
    this.cssaEventService = cssaEventService;
  }

  @PostMapping("/")
  public ResponseEntity<?> createEvent(@RequestBody CSSAEventRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();
    try {
      CSSAEvent newEvent = cssaEventService.createEvent(
          requestBody.getEventName(),
          requestBody.getEventStartDate(),
          requestBody.getEventEndDate(),
          requestBody.getEventLocation(),
          requestBody.getEventImageUrl(),
          requestBody.getEventDescription(),
          requestBody.getEventLinkUrl());

      response.put("message", "Event created");
      response.put("event", new CSSAEventDTO(newEvent));
      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to create event.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (IOException e) {
      response.put("message", "Failed to create event. Invalid URL.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (DataAccessException e) {
      response.put("message", "Failed to create event. Database access error.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/name/{eventName}")
  public ResponseEntity<?> getEventByName(@PathVariable String eventName) {
    Optional<CSSAEvent> optionalEvent = cssaEventService.findEventByName(eventName);
    Map<String, Object> response = new HashMap<>();
    if (optionalEvent.isPresent()) {
      CSSAEvent event = optionalEvent.get();
      response.put("message", "Event found with name " + eventName);
      response.put("event", new CSSAEventDTO(event));
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "Event not found with name " + eventName);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/upcoming/{limit}")
  public ResponseEntity<?> getUpcomingEvents(@PathVariable int limit) {
    List<CSSAEvent> events = cssaEventService.findUpcomingEvents(limit);
    Map<String, Object> response = new HashMap<>();
    if (!events.isEmpty()) {
      response.put("message", "Upcoming events found");
      response.put("events", events); // Here I'm directly using the CSSAEvent object, if you want to use DTOs, you
                                      // should map these to CSSAEventDto.
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "No upcoming events found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PutMapping("/name/{eventName}")
  public ResponseEntity<?> updateEventByName(@PathVariable String eventName,
      @RequestBody CSSAEventRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();
    try {
      boolean updated = cssaEventService.updateEventByName(
          eventName,
          requestBody.getEventName(),
          requestBody.getEventStartDate(),
          requestBody.getEventEndDate(),
          requestBody.getEventLocation(),
          requestBody.getEventImageUrl(),
          requestBody.getEventDescription(),
          requestBody.getEventLinkUrl());

      if (updated) {
        response.put("message", "Event updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
      } else {
        response.put("message", "Event update failed. No such event found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }

    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to update event.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (IOException e) {
      response.put("message", "Failed to update event. Invalid URL.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (DataAccessException e) {
      response.put("message", "Failed to update event. Database access error.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllEvents() {
    List<CSSAEvent> events = cssaEventService.getAllEvents();
    Map<String, Object> response = new HashMap<>();
    if (!events.isEmpty()) {
      response.put("message", "All events retrieved successfully");
      response.put("events", events);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "No events found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/name/{name}")
  public ResponseEntity<?> deleteEventByName(@PathVariable String name) {
    Map<String, Object> response = new HashMap<>();
    boolean deleted = cssaEventService.deleteEventByName(name);
    if (deleted) {
      response.put("message", "Event with name " + name + " successfully deleted");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    response.put("message", "Event with name " + name + " not found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @DeleteMapping("/all")
  public ResponseEntity<?> deleteAllEvents() {
    boolean deleted = cssaEventService.deleteAllEvents();
    if (deleted) {
      return ResponseEntity.ok("All events deleted successfully");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No events found");
  }

  @Data
  @ToString
  // Inner class for the request body
  public static class CSSAEventRequestBody {
    private String eventName;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private String eventLocation;
    private String eventImageUrl;
    private String eventDescription;
    private String eventLinkUrl;

    // Getters and setters
  }
}
