package ca.mcgillcssa.cssabackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

import ca.mcgillcssa.cssabackend.model.Registration;

@Repository
public class RegistrationRepository {

  private final MongoTemplate mongoTemplate;

  public RegistrationRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Registration saveRegistration(Registration registration) {
    return mongoTemplate.save(registration);
  }

  public List<Registration> findByName(String name) {
    Query query = new Query(Criteria.where("name").is(name));
    return mongoTemplate.find(query, Registration.class);
  }

  public List<Registration> findByTicketName(String ticketName) {
    Query query = new Query(Criteria.where("ticketName").is(ticketName));
    return mongoTemplate.find(query, Registration.class);
  }

  public Optional<Registration> findByNameAndTicketName(String name, String ticketName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    query.addCriteria(Criteria.where("ticketName").is(ticketName));
    return Optional.ofNullable(mongoTemplate.findOne(query, Registration.class));
}

  public List<Registration> findByEmail(String email) {
    Query query = new Query(Criteria.where("email").is(email));
    return mongoTemplate.find(query, Registration.class);
  }

  public List<Registration> findByWechatId(String wechatId) {
    Query query = new Query(Criteria.where("wechatId").is(wechatId));
    return mongoTemplate.find(query, Registration.class);
  }

  public List<Registration> findAll() {
    return mongoTemplate.findAll(Registration.class);
  }

  public boolean deleteByName(String name) {
    Query query = new Query(Criteria.where("name").is(name));
    DeleteResult result = mongoTemplate.remove(query, Registration.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteByTicketName(String ticketName) {
    Query query = new Query(Criteria.where("ticketName").is(ticketName));
    DeleteResult result = mongoTemplate.remove(query, Registration.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteByNameAndTicketName(String name, String ticketName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("name").is(name));
    query.addCriteria(Criteria.where("ticketName").is(ticketName));
    DeleteResult result = mongoTemplate.remove(query, Registration.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteByEmail(String email) {
    Query query = new Query(Criteria.where("email").is(email));
    DeleteResult result = mongoTemplate.remove(query, Registration.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteByWechatId(String wechatId) {
    Query query = new Query(Criteria.where("wechatId").is(wechatId));
    DeleteResult result = mongoTemplate.remove(query, Registration.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteAll() {
    DeleteResult result = mongoTemplate.remove(new Query(), Registration.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

}
