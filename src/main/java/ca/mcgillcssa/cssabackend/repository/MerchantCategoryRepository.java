package ca.mcgillcssa.cssabackend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.mongodb.client.result.DeleteResult;
import ca.mcgillcssa.cssabackend.model.MerchantCategory;

@Repository
public class MerchantCategoryRepository {

  private final MongoTemplate mongoTemplate;

  public MerchantCategoryRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  // Create or Update a MerchantCategory
  public MerchantCategory save(MerchantCategory merchantCategory) {
    return mongoTemplate.save(merchantCategory);
  }

  // Retrieve a MerchantCategory by its type
  public Optional<MerchantCategory> findByMerchantType(String merchantType) {
    Query query = new Query(Criteria.where("merchantType").is(merchantType));
    return Optional.ofNullable(mongoTemplate.findOne(query, MerchantCategory.class));
  }

  // Retrieve all MerchantCategories
  public List<MerchantCategory> findAll() {
    return mongoTemplate.findAll(MerchantCategory.class);
  }

  // Delete a MerchantCategory by its type
  public boolean deleteByMerchantType(String merchantType) {
    Query query = new Query(Criteria.where("merchantType").is(merchantType));
    DeleteResult result = mongoTemplate.remove(query, MerchantCategory.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }

  public boolean deleteAll() {
    DeleteResult result = mongoTemplate.remove(new Query(), MerchantCategory.class);
    return result.wasAcknowledged() && result.getDeletedCount() > 0;
  }
}
