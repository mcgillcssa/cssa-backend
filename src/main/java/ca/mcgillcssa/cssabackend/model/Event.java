package ca.mcgillcssa.cssabackend.model;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Document(collection = "events")
@Data
@AllArgsConstructor
@ToString

public class Event {
  @Id
  private int id;
  private String eventName;
  private Date eventStartDate;
  private Date eventEndDate;
  private String eventLocation;
  private String eventImageUrl;
  private String eventDescription;
  private String eventLinkUrl;
}
