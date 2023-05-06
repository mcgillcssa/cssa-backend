package ca.mcgillcssa.cssabackend.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

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

  public Optional<Member> findBySchoolEmail(String schoolEmail) {
    Query query = new Query(Criteria.where("schoolEmail").is(schoolEmail));
    return Optional.ofNullable(mongoTemplate.findOne(query, Member.class));
  }

  public List<Member> findByBirthdayMonth(int month) {
    Date start = getMonthStart(month);
    Date end = getMonthEnd(month);

    Query query = new Query();
    query.addCriteria(Criteria.where("birthday").gte(start).lt(end));

    return mongoTemplate.find(query, Member.class);
  }

  private Date getMonthStart(int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2000);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  private Date getMonthEnd(int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 2000);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTime();
  }

  public boolean deleteByPersonalEmail(String personalEmail) {
    Query query = new Query(Criteria.where("personalEmail").is(personalEmail));
    DeleteResult result = mongoTemplate.remove(query, Member.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteBySchoolEmail(String schoolEmail) {
    Query query = new Query(Criteria.where("schoolEmail").is(schoolEmail));
    DeleteResult result = mongoTemplate.remove(query, Member.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }
}
