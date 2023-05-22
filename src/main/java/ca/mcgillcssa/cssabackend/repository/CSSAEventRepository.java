package ca.mcgillcssa.cssabackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import ca.mcgillcssa.cssabackend.model.CSSAEvent;

@Repository
public class CSSAEventRepository {
  private final MongoTemplate mongoTemplate;

  public CSSAEventRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public CSSAEvent saveEvent(CSSAEvent event) {
    return mongoTemplate.save(event);
  }

  public Optional<CSSAEvent> findEventById(String id) {
    CSSAEvent event = mongoTemplate.findById(id, CSSAEvent.class);
    return Optional.ofNullable(event);
  }
}
