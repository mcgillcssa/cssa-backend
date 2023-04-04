package ca.mcgillcssa.cssabackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ca.mcgillcssa.cssabackend.model.Member;

@Repository
public class MemberRepository {

  private final MongoTemplate mongoTemplate;

  public MemberRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Member createMember(Member member) {
    return mongoTemplate.save(member);
  }

  public Optional<Member> findByPersonalEmail(String personalEmail) {
    Query query = new Query(Criteria.where("personalEmail").is(personalEmail));
    return Optional.ofNullable(mongoTemplate.findOne(query, Member.class));
  }

  public void deleteByPersonalEmail(String personalEmail) {
    Query query = new Query(Criteria.where("personalEmail").is(personalEmail));
    mongoTemplate.remove(query, Member.class);
  }
}
