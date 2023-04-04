package ca.mcgillcssa.cssabackend.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoSocketException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

  @Value("${spring.data.mongodb.uri}")
  private String uri;

  @Value("${spring.data.mongodb.database}")
  private String database;

  @Bean
  public MongoClient mongoClient() {
    try {
      MongoClientSettings settings = MongoClientSettings.builder()
          .applyConnectionString(new ConnectionString(uri))
          .serverApi(ServerApi.builder()
              .version(ServerApiVersion.V1)
              .build())
          .build();

      MongoClient mongoClient = MongoClients.create(settings);
      System.out.println("Connected to MongoDB!");
      return mongoClient;
    } catch (MongoSocketException e) {
      System.err.println("Error connecting to MongoDB: " + e.getMessage());
      return null;
    }
  }

  @Bean
  public MongoDatabase mongoDatabase() {
    return mongoClient().getDatabase(database);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoClient(), database);
  }
}
