package ca.mcgillcssa.cssabackend.dto;

import ca.mcgillcssa.cssabackend.model.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDTO {
  private String ticketName;
  private int earlyBirdTotal;
  private int earlyBirdRemain;
  private int regularTotal;
  private int regularRemain;

  public TicketDTO(Ticket ticket) {
    this.ticketName = ticket.getTicketName();
    this.earlyBirdTotal = ticket.getEarlyBirdTotal();
    this.earlyBirdRemain = ticket.getEarlyBirdRemain();
    this.regularTotal = ticket.getRegularTotal();
    this.regularRemain = ticket.getRegularRemain();
  }
}

