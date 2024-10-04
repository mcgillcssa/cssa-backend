package ca.mcgillcssa.cssabackend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document(collection = "tickets")
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "ticketName") // Ensure uniqueness based on ticketName
public class Ticket {
  private String ticketName;
  private Integer earlyBirdNum;
  private Integer regularNum;
}
