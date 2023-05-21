package ca.mcgillcssa.cssabackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import ca.mcgillcssa.cssabackend.model.Sponsor;

@Repository
public class SponsorRepository {

    private final MongoTemplate mongoTemplate;

    public SponsorRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // Create single
    public Sponsor createSponsor(Sponsor sponsor) {
        return mongoTemplate.save(sponsor);
    }

    // Find single
    public Optional<Sponsor> findSponsorByName(String sponsorName) {
        Query query = new Query(Criteria.where("sponsorName").is(sponsorName));
        return Optional.ofNullable(mongoTemplate.findOne(query, Sponsor.class));
    }

    // Find many
    public List<Sponsor> findAllSponsors() {
        return mongoTemplate.findAll(Sponsor.class);
    }

    public List<Sponsor> findSponsorsByClass(String sponsorClass) {
        return mongoTemplate.find(new Query(Criteria.where("sponsorClass").is(sponsorClass)), Sponsor.class);
    }

    public List<Sponsor> findSponsorsByCoopDuration(String coopDuration) {
        return mongoTemplate.find(new Query(Criteria.where("coopDuration").is(coopDuration)), Sponsor.class);
    }

    // Delete single
    public boolean deleteSponsorByName(String sponsorName) {
        Query query = new Query(Criteria.where("sponsorName").is(sponsorName));
        DeleteResult deleteResult = mongoTemplate.remove(query, Sponsor.class);
        return deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() > 0;
    }

    // Update single
    public boolean updateSponsor(String name, String coopDuration, String sponsorImageUrl, String sponsorWebsiteUrl,
            String sponsorClass) {
        Query query = new Query(Criteria.where("sponsorName").is(name));
        Update update = new Update();

        if (coopDuration != null && !coopDuration.isEmpty()) {
            update.set("coopDuration", coopDuration);
        }

        if (sponsorImageUrl != null && !sponsorImageUrl.isEmpty()) {
            update.set("sponsorImageUrl", sponsorImageUrl);
        }

        if (sponsorWebsiteUrl != null && !sponsorWebsiteUrl.isEmpty()) {
            update.set("sponsorWebsiteUrl", sponsorWebsiteUrl);
        }

        if (sponsorClass != null && !sponsorClass.isEmpty()) {
            update.set("sponsorClass", sponsorClass);
        }

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Sponsor.class);
        return updateResult.wasAcknowledged() && updateResult.getModifiedCount() > 0;
    }

}
