package ca.mcgillcssa.cssabackend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.Registration;
import ca.mcgillcssa.cssabackend.model.Ticket;
import ca.mcgillcssa.cssabackend.model.Registration.RegistrationType;
import ca.mcgillcssa.cssabackend.repository.RegistrationRepository;
import ca.mcgillcssa.cssabackend.repository.TicketRepository;

@Service
public class RegistrationService {

  private final TicketRepository ticketRepository;
  private final RegistrationRepository registrationRepository;

  public RegistrationService(TicketRepository ticketRepository, RegistrationRepository registrationRepository) {
    this.ticketRepository = ticketRepository;
    this.registrationRepository = registrationRepository;
  }

  public Ticket createTicket(String ticketName, Integer earlyBirdTotal, Integer earlyBirdRemain, Integer regularTotal, Integer regularRemain) {
    if (ticketName == null || earlyBirdTotal == null || earlyBirdRemain == null || regularTotal == null || regularRemain == null) {
      throw new IllegalArgumentException("Ticket Name and Number of Tickets are required.");
    }
    if (earlyBirdTotal < 0 || earlyBirdRemain < 0 || regularTotal < 0 || regularRemain < 0) {
      throw new IllegalArgumentException("Number of tickets should be equal or greater than 0.");
    }
    if (findByTicketName(ticketName).isPresent()) {
      throw new IllegalArgumentException("A ticket with the name " + ticketName + " already exists.");
    }

    Ticket newTicket = new Ticket(ticketName, earlyBirdTotal, earlyBirdRemain, regularTotal, regularRemain);
    return ticketRepository.saveTicket(newTicket);
  }

  public Optional<Ticket> findByTicketName(String ticketName) {
    return ticketRepository.findByTicketName(ticketName);
  }

  public List<Ticket> findAllTickets() {
    return ticketRepository.findAll();
  }

  public boolean deleteByTicketName(String ticketName) {
    return ticketRepository.deleteByTicketName(ticketName);
  }

  public boolean deleteAllTickets() {
    return ticketRepository.deleteAll();
  }

  public boolean updateTicket(String ticketName, Integer earlyBirdTotal, Integer earlyBirdRemain, Integer regularTotal, Integer regularRemain) {
    Ticket existingTicket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket with name " + ticketName + " does not exist."));

    if (earlyBirdTotal.equals(existingTicket.getEarlyBirdTotal()) && earlyBirdRemain.equals(existingTicket.getEarlyBirdRemain()) && regularTotal.equals(existingTicket.getRegularTotal()) && regularRemain.equals(existingTicket.getRegularRemain())) {
      throw new IllegalArgumentException("Nothing to be changed.");
    }

    if (earlyBirdTotal != null) existingTicket.setEarlyBirdTotal(earlyBirdTotal);
    if (earlyBirdRemain != null) existingTicket.setEarlyBirdRemain(earlyBirdRemain);
    if (regularTotal != null) existingTicket.setRegularTotal(regularTotal);
    if (regularRemain != null) existingTicket.setRegularRemain(regularRemain);

    ticketRepository.saveTicket(existingTicket);
    return true;
  }

  public Registration createRegistration(String id, String name, String email, String wechatId, LocalDate registrationDate, RegistrationType registrationType) {
    if (name == null || email == null || registrationDate == null || registrationType == null) {
      throw new IllegalArgumentException("Name, Email, RegistrationDate and RegistrationType are required.");
    }

    Registration newRegistration = new Registration(id, name, email, wechatId, registrationDate, registrationType);
    return registrationRepository.saveRegistration(newRegistration);
  }

  public Optional<Registration> findByName(String name) {
    return registrationRepository.findByName(name);
  }

  public Optional<Registration> findByEmail(String email) {
    return registrationRepository.findByEmail(email);
  }

  public Optional<Registration> findByWechatId(String wechatId) {
    return registrationRepository.findByWechatId(wechatId);
  }

  public List<Registration> findAllRegistrations() {
    return registrationRepository.findAll();
  }

  public boolean deleteByName(String name) {
    return registrationRepository.deleteByName(name);
  }

  public boolean deleteByEmail(String email) {
    return registrationRepository.deleteByEmail(email);
  }

  public boolean deleteByWechatId(String wechatId) {
    return registrationRepository.deleteByWechatId(wechatId);
  }

  public boolean deleteAllRegistrations() {
    return registrationRepository.deleteAll();
  }

  public int getEarlyBirdTotal(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getEarlyBirdTotal();
  }

  public int getEarlyBirdRemain(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getEarlyBirdRemain();
  }

  public int getRegularTotal(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getRegularTotal();
  }

  public int getRegularRemain(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getRegularRemain();
  }

  public boolean purchaseEarlyBird(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    if (ticket.getEarlyBirdRemain() <= 0)
      throw new IllegalArgumentException("No enough EarlyBird tickets.");
    ticket.setEarlyBirdRemain(ticket.getEarlyBirdRemain() - 1);
    ticketRepository.saveTicket(ticket);
    return true;
  }

  public boolean purchaseRegular(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    if (ticket.getRegularRemain() <= 0)
      throw new IllegalArgumentException("No enough Regular tickets.");
    ticket.setRegularRemain(ticket.getRegularRemain() - 1);
    ticketRepository.saveTicket(ticket);
    return true;
  }

}
