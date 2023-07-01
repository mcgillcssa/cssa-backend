package ca.mcgillcssa.cssabackend.repository;

import java.util.List;
import java.util.Optional;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ca.mcgillcssa.cssabackend.model.MembershipBenefit;

@Repository

public class MemebershipBenefitRepository {
  private final MongoTemplate mongoTemplate;

  public MemebershipBenefitRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public MembershipBenefit createMemebershipBenefit(MembershipBenefit membershipBenefit) {
    return mongoTemplate.save(membershipBenefit);
  }

  public List<MembershipBenefit> findAll() {
    return mongoTemplate.findAll(MembershipBenefit.class);
  }

  public Optional<MembershipBenefit> findMembershipBenefitByMerchantName(String merchantName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("merchantName").is(merchantName));
    MembershipBenefit membershipBenefit = mongoTemplate.findOne(query, MembershipBenefit.class);
    return Optional.ofNullable(membershipBenefit);
  }

  public void save(MembershipBenefit membershipBenefit) {
    mongoTemplate.save(membershipBenefit);
  }

  public boolean deleteAll() {
    Query query = new Query();
    DeleteResult result = mongoTemplate.remove(query, MembershipBenefit.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteByName(String merchantName) {
    Query query = new Query(Criteria.where("merchantName").is(merchantName));
    DeleteResult result = mongoTemplate.remove(query, MembershipBenefit.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }
}
