package ca.mcgillcssa.cssabackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

import ca.mcgillcssa.cssabackend.model.Ticket;

@Repository
public class TicketRepository {

  private final MongoTemplate mongoTemplate;

  public TicketRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Ticket saveTicket(Ticket ticket) {
    return mongoTemplate.save(ticket);
  }

  public Optional<Ticket> findByTicketName(String ticketName) {
    Query query = new Query(Criteria.where("ticketName").is(ticketName));
    return Optional.ofNullable(mongoTemplate.findOne(query, Ticket.class));
  }

  public List<Ticket> findAll() {
    return mongoTemplate.findAll(Ticket.class);
  }

  public boolean deleteByTicketName(String ticketName) {
    Query query = new Query(Criteria.where("ticketName").is(ticketName));
    DeleteResult result = mongoTemplate.remove(query, Ticket.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteAll() {
    DeleteResult result = mongoTemplate.remove(new Query(), Ticket.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

}
