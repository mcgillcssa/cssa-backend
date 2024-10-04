package ca.mcgillcssa.cssabackend.dto;

import ca.mcgillcssa.cssabackend.model.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDTO {
  private String ticketName;
  private Integer earlyBirdNum;
  private Integer regularNum;

  public TicketDTO(Ticket ticket) {
    this.ticketName = ticket.getTicketName();
    this.earlyBirdNum = ticket.getEarlyBirdNum();
    this.regularNum = ticket.getRegularNum();
  }
}

