package ca.mcgillcssa.cssabackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.Ticket;
import ca.mcgillcssa.cssabackend.repository.TicketRepository;

@Service
public class TicketService {

  private final TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public Ticket createTicket(String ticketName, Integer earlyBirdNum, Integer regularNum) {
    if (ticketName == null || earlyBirdNum == null || regularNum == null ) {
      throw new IllegalArgumentException("Ticket Name and Number of Tickets are required.");
    }
    if (earlyBirdNum < 0 || regularNum < 0 ) {
      throw new IllegalArgumentException("Number of tickets should be equal or greater than 0.");
    }
    if (findByTicketName(ticketName).isPresent()) {
      throw new IllegalArgumentException("A ticket with the name " + ticketName + " already exists.");
    }

    Ticket newTicket = new Ticket(ticketName, earlyBirdNum, regularNum);
    return ticketRepository.saveTicket(newTicket);
  }

  public Optional<Ticket> findByTicketName(String ticketName) {
    return ticketRepository.findByTicketName(ticketName);
  }

  public List<Ticket> findAll() {
    return ticketRepository.findAll();
  }

  public boolean deleteByTicketName(String ticketName) {
    return ticketRepository.deleteByTicketName(ticketName);
  }

  public boolean deleteAll() {
    return ticketRepository.deleteAll();
  }

  public boolean updateTicket(String ticketName, Integer earlyBirdNum, Integer regularNum) {
    Ticket existingTicket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket with name " + ticketName + " does not exist."));

    if (earlyBirdNum.equals(existingTicket.getEarlyBirdNum()) && regularNum.equals(existingTicket.getRegularNum())) {
      throw new IllegalArgumentException("Nothing to be changed.");
    }

    existingTicket.setEarlyBirdNum(earlyBirdNum);
    existingTicket.setRegularNum(regularNum);
    ticketRepository.deleteByTicketName(ticketName);
    ticketRepository.saveTicket(existingTicket);
    return true;
  }

}
