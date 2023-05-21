package ca.mcgillcssa.cssabackend.repository;

import java.util.List;
import java.util.Optional;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ca.mcgillcssa.cssabackend.model.CSSAEvent;
import java.time.LocalDate;

@Repository
public class CSSAEventRepository {
  private final MongoTemplate mongoTemplate;

  public CSSAEventRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public CSSAEvent createEvent(CSSAEvent event) {
    return mongoTemplate.save(event);
  }

  public Optional<CSSAEvent> findEventById(String id) {
    CSSAEvent event = mongoTemplate.findById(id, CSSAEvent.class);
    return Optional.ofNullable(event);
  }

  public Optional<List<CSSAEvent>> findAllEvents() {
    List<CSSAEvent> CSSAEvents = mongoTemplate.findAll(CSSAEvent.class);
    return Optional.ofNullable(CSSAEvents);
  }

  public boolean updateCSSAEvent(String id, String eventName, LocalDate eventStartDate, LocalDate eventEndDate,
      String eventLocation, String eventImageUrl, String eventDescription, String eventLinkUrl) {
    Query query = new Query(Criteria.where("id").is(id));
    Update update = new Update();

    if (eventName != null && !eventName.isEmpty()) {
      update.set("eventName", eventName);
    }

    if (eventStartDate.toString() != null && !eventStartDate.toString().isEmpty()) {
      update.set("eventStartDate", eventStartDate);
    }

    if (eventEndDate.toString() != null && !eventEndDate.toString().isEmpty()) {
      update.set("eventEndDate", eventEndDate);
    }

    if (eventLocation != null && !eventLocation.isEmpty()) {
      update.set("eventLocation", eventLocation);
    }

    if (eventImageUrl != null && !eventImageUrl.isEmpty()) {
      update.set("eventImageUrl", eventImageUrl);
    }

    if (eventDescription != null && !eventDescription.isEmpty()) {
      update.set("eventDescription", eventDescription);
    }

    if (eventLinkUrl != null && !eventLinkUrl.isEmpty()) {
      update.set("eventLinkUrl", eventLinkUrl);
    }

    UpdateResult updateResult = mongoTemplate.updateFirst(query, update, CSSAEvent.class);
    return updateResult.wasAcknowledged() && updateResult.getModifiedCount() > 0;
  }

  public boolean deleteById(String id) {
    Query query = new Query(Criteria.where("id").is(id));
    DeleteResult result = mongoTemplate.remove(query, CSSAEvent.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public List<CSSAEvent> findUpcomingEvents(int limit) {
    Query query = new Query();
    query.with(Sort.by(Sort.Direction.ASC, "eventStartDate")); // Sort events by start date
    query.limit(limit); // Limit the number of returned events

    // Return only future events
    query.addCriteria(Criteria.where("eventStartDate").gt(LocalDate.now()));

    return mongoTemplate.find(query, CSSAEvent.class);
  }

}
