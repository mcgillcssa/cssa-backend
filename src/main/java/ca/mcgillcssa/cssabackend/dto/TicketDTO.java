package ca.mcgillcssa.cssabackend.dto;

import ca.mcgillcssa.cssabackend.model.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDTO {
  private String ticketName;
  private String ticketDate;

  private int earlyBirdTotal;
  private int earlyBirdRemain;
  private int earlyBirdPrice;
  private int regularTotal;
  private int regularRemain;
  private int regularPrice;

  public TicketDTO(Ticket ticket) {
    this.ticketName = ticket.getTicketName();
    this.ticketDate = ticket.getTicketDate().toString();

    this.earlyBirdTotal = ticket.getEarlyBirdTotal();
    this.earlyBirdRemain = ticket.getEarlyBirdRemain();
    this.earlyBirdPrice = ticket.getEarlyBirdPrice();
    this.regularTotal = ticket.getRegularTotal();
    this.regularRemain = ticket.getRegularRemain();
    this.regularPrice = ticket.getRegularPrice();
  }
}

