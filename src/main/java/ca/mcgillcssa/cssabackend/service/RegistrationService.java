package ca.mcgillcssa.cssabackend.service;

import java.io.IOException;
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

  public Ticket createTicket(String ticketName, LocalDate ticketDate, Integer earlyBirdTotal, Integer earlyBirdRemain, Integer earlyBirdPrice, Integer regularTotal, Integer regularRemain, Integer regularPrice) throws IOException {
    if (ticketName == null || earlyBirdTotal == null || earlyBirdRemain == null || earlyBirdPrice == null || regularTotal == null || regularRemain == null || regularPrice == null) {
      throw new IllegalArgumentException("Ticket Name and Number/Prices of Tickets are required.");
    }
    if (ticketDate == null) ticketDate = LocalDate.now();
    if (earlyBirdTotal < 0 || earlyBirdRemain < 0 || earlyBirdPrice < 0 || regularTotal < 0 || regularRemain < 0 || regularPrice < 0) {
      throw new IllegalArgumentException("Number of tickets should be equal or greater than 0.");
    }
    if (findByTicketName(ticketName).isPresent()) {
      throw new IllegalArgumentException("A ticket with the name " + ticketName + " already exists.");
    }

    Ticket newTicket = new Ticket(ticketName, ticketDate, earlyBirdTotal, earlyBirdRemain, earlyBirdPrice, regularTotal, regularRemain, regularPrice);
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

  public LocalDate getTicketDate(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getTicketDate();
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

  public int getEarlyBirdPrice(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getEarlyBirdPrice();
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

  public int getRegularPrice(String ticketName) {
    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    return ticket.getRegularPrice();
  }

  /*
  public boolean updateTicket(String ticketName, LocalDate ticketDate, Integer earlyBirdTotal, Integer earlyBirdRemain, Integer earlyBirdPrice, Integer regularTotal, Integer regularRemain, Integer regularPrice) throws IOException {
    Ticket existingTicket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket with name " + ticketName + " does not exist."));

    if (ticketDate.equals(existingTicket.getTicketDate()) && earlyBirdTotal.equals(existingTicket.getEarlyBirdTotal()) && earlyBirdRemain.equals(existingTicket.getEarlyBirdRemain()) && earlyBirdPrice.equals(existingTicket.getEarlyBirdPrice()) && regularTotal.equals(existingTicket.getRegularTotal()) && regularRemain.equals(existingTicket.getRegularRemain()) && regularPrice.equals(existingTicket.getRegularPrice())) {
      throw new IllegalArgumentException("Nothing to be changed.");
    }

    if ((earlyBirdTotal != null && earlyBirdTotal < 0) || (earlyBirdRemain != null && earlyBirdRemain < 0) || (earlyBirdPrice != null && earlyBirdPrice < 0) || (regularTotal != null && regularTotal < 0) || (regularRemain != null && regularRemain < 0) || (regularPrice != null && regularPrice < 0)) {
      throw new IllegalArgumentException("Number of tickets should be equal or greater than 0.");
    }

    if (ticketDate != null) existingTicket.setTicketDate(ticketDate);
    if (earlyBirdTotal != null) existingTicket.setEarlyBirdTotal(earlyBirdTotal);
    if (earlyBirdRemain != null) existingTicket.setEarlyBirdRemain(earlyBirdRemain);
    if (earlyBirdPrice != null) existingTicket.setEarlyBirdPrice(earlyBirdPrice);
    if (regularTotal != null) existingTicket.setRegularTotal(regularTotal);
    if (regularRemain != null) existingTicket.setRegularRemain(regularRemain);
    if (regularPrice != null) existingTicket.setRegularPrice(regularPrice);;

    ticketRepository.saveTicket(existingTicket);
    return true;
  }
  */

  public Registration createRegistration(String name, String ticketName, String email, String wechatId, LocalDate registrationDate, RegistrationType registrationType) throws IOException {
    if (name == null || ticketName == null || email == null ||  registrationType == null) {
      throw new IllegalArgumentException("Name, TicketName, Email, RegistrationDate and RegistrationType are required.");
    }
    if (!findByTicketName(ticketName).isPresent()) {
      throw new IllegalArgumentException("A ticket with the name " + ticketName + " doesn't exist.");
    }
    if (registrationDate == null) registrationDate = LocalDate.now();
    Registration newRegistration = new Registration(name, ticketName, email, wechatId, registrationDate, registrationType);
    return registrationRepository.saveRegistration(newRegistration);
  }

  public List<Registration> findByName(String name) {
    return registrationRepository.findByName(name);
  }

  public List<Registration> findRByTicketName(String ticketName) {
    return registrationRepository.findByTicketName(ticketName);
  }

  public Optional<Registration> findByNameAndTicketName(String name, String ticketName) {
    return registrationRepository.findByNameAndTicketName(name, ticketName);
  }

  public List<Registration> findByEmail(String email) {
    return registrationRepository.findByEmail(email);
  }

  public List<Registration> findByWechatId(String wechatId) {
    return registrationRepository.findByWechatId(wechatId);
  }

  public List<Registration> findAllRegistrations() {
    return registrationRepository.findAll();
  }

  public boolean deleteByName(String name) {
    return registrationRepository.deleteByName(name);
  }

  public boolean deleteRByTicketName(String ticketName) {
    return registrationRepository.deleteByTicketName(ticketName);
  }

  public boolean deleteByNameAndTicketName(String name, String ticketName) {
    return registrationRepository.deleteByNameAndTicketName(name, ticketName);
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

  /*
  public boolean updateRegistration(String name, String ticketName, String email, String wechatId, LocalDate registrationDate, RegistrationType registrationType) throws IOException {
    Registration existingRegistration = findByNameAndTicketName(name, ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException(name + ", registration for " + ticketName + " does not exist."));

    if (email.equals(existingRegistration.getEmail()) && wechatId.equals(existingRegistration.getWechatId()) && registrationDate.equals(existingRegistration.getRegistrationDate()) && registrationType.equals(existingRegistration.getRegistrationType())) {
      throw new IllegalArgumentException("Nothing to be changed.");
    }

    if (email != null) existingRegistration.setEmail(email);
    if (wechatId!= null) existingRegistration.setWechatId(wechatId);
    if (registrationDate!= null) existingRegistration.setRegistrationDate(registrationDate);
    if (registrationType!= null) existingRegistration.setRegistrationType(registrationType);

    registrationRepository.saveRegistration(existingRegistration);

    return true;
  }
  */

  public boolean purchaseEarlyBird(String ticketName, String buyerName, String email, String wechatId, LocalDate registrationDate) throws IOException {

    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    if (ticket.getEarlyBirdRemain() <= 0)
      throw new IllegalArgumentException("No enough EarlyBird tickets.");
    ticket.setEarlyBirdRemain(ticket.getEarlyBirdRemain() - 1);
    ticketRepository.saveTicket(ticket);

    createRegistration(buyerName, ticketName, email, wechatId, registrationDate, RegistrationType.EARLYBIRD);

    return true;
  }

  public boolean purchaseRegular(String ticketName, String buyerName, String email, String wechatId, LocalDate registrationDate) throws IOException {

    Ticket ticket = findByTicketName(ticketName)
        .orElseThrow(
            () -> new IllegalArgumentException("Ticket type '" + ticketName + "' does not exist."));
    if (ticket.getRegularRemain() <= 0)
      throw new IllegalArgumentException("No enough Regular tickets.");
    ticket.setRegularRemain(ticket.getRegularRemain() - 1);
    ticketRepository.saveTicket(ticket);

    createRegistration(buyerName, ticketName, email, wechatId, registrationDate, RegistrationType.REGULAR);

    return true;
  }

}
