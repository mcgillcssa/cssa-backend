package ca.mcgillcssa.cssabackend.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

  public List<CSSAEvent> findAll() {
    return mongoTemplate.findAll(CSSAEvent.class);
  }

  public Optional<CSSAEvent> findEventByName(String eventName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("eventName").is(eventName));

    CSSAEvent event = mongoTemplate.findOne(query, CSSAEvent.class);
    return Optional.ofNullable(event);
  }

  public Optional<List<CSSAEvent>> findAllEvents() {
    List<CSSAEvent> CSSAEvents = mongoTemplate.findAll(CSSAEvent.class);
    return Optional.ofNullable(CSSAEvents);
  }

  public void save(CSSAEvent event) {
    mongoTemplate.save(event);
  }

  public boolean deleteAll() {
    Query query = new Query();
    DeleteResult result = mongoTemplate.remove(query, CSSAEvent.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteByName(String eventName) {
    Query query = new Query(Criteria.where("eventName").is(eventName));
    DeleteResult result = mongoTemplate.remove(query, CSSAEvent.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public List<CSSAEvent> findUpcomingEvents(int limit) {
    List<CSSAEvent> events = new ArrayList<>();

    Query upcomingQuery = new Query();
    upcomingQuery.with(Sort.by(Sort.Direction.ASC, "eventStartDate")); // Sort events by start date
    upcomingQuery.addCriteria(Criteria.where("eventStartDate").gt(LocalDate.now()));
    upcomingQuery.limit(limit); // Limit the number of returned events

    List<CSSAEvent> upcomingEvents = mongoTemplate.find(upcomingQuery, CSSAEvent.class);
    events.addAll(upcomingEvents);

    if (upcomingEvents.size() < limit) {
      int pastEventsLimit = limit - upcomingEvents.size();

      Query pastQuery = new Query();
      pastQuery.with(Sort.by(Sort.Direction.DESC, "eventStartDate")); // Sort past events by start date in descending
                                                                      // order
      pastQuery.addCriteria(Criteria.where("eventStartDate").lt(LocalDate.now()));
      pastQuery.limit(pastEventsLimit); // Limit the number of returned past events

      List<CSSAEvent> pastEvents = mongoTemplate.find(pastQuery, CSSAEvent.class);
      events.addAll(pastEvents);
    }

    // Sort all events in ascending order by date
    events.sort(Comparator.comparing(CSSAEvent::getEventStartDate));

    return events;
  }

}
