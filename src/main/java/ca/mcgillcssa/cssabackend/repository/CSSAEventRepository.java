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
    Query query = new Query();
    query.with(Sort.by(Sort.Direction.ASC, "eventStartDate")); // Sort events by start date
    query.limit(limit); // Limit the number of returned events

    // Return only future events
    query.addCriteria(Criteria.where("eventStartDate").gt(LocalDate.now()));

    return mongoTemplate.find(query, CSSAEvent.class);
  }

}
