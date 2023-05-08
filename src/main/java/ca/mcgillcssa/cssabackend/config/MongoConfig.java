package ca.mcgillcssa.cssabackend.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  private String uri = "mongodb+srv://itmcgillcssa:" + System.getenv("DB_PASSWORD")
      + "@cssa-backend.dex7vrz.mongodb.net/?retryWrites=true&w=majority";

  @Override
  protected String getDatabaseName() {
    return "cssa";
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString(uri);
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Override
  public Collection<String> getMappingBasePackages() {
    return Collections.singleton("ca.mcgillcssa");
  }

  @Override
  protected boolean autoIndexCreation() {
    return true;
  }
}
