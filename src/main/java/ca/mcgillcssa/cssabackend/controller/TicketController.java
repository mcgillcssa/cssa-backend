package ca.mcgillcssa.cssabackend.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgillcssa.cssabackend.dto.TicketDTO;
import ca.mcgillcssa.cssabackend.model.Ticket;
import ca.mcgillcssa.cssabackend.service.RegistrationService;
import lombok.Data;
import lombok.ToString;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final RegistrationService registrationService;

    public TicketController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createTicket(@RequestBody TicketRequestBody requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Ticket newTicket = registrationService.createTicket(
                requestBody.getTicketName(),
                requestBody.getTicketDate(),
                requestBody.getEarlyBirdTotal(),
                requestBody.getEarlyBirdRemain(),
                requestBody.getEarlyBirdPrice(),
                requestBody.getRegularTotal(),
                requestBody.getRegularRemain(),
                requestBody.getRegularPrice()
            );
            response.put("message", "Ticket created");
            response.put("ticket", new TicketDTO(newTicket));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "Failed to create ticket.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            response.put("message", "Failed to create ticket. Invalid URL.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (DataAccessException e) {
            response.put("message", "Failed to create ticket. Database access error.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/name/{ticketName}")
    public ResponseEntity<?> findTicketByName(@PathVariable String ticketName) {
        Optional<Ticket> optionalTicket = registrationService.findByTicketName(ticketName);
        Map<String, Object> response = new HashMap<>();
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            response.put("message", "Ticket found with name " + ticketName);
            response.put("ticket", new TicketDTO(ticket));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Ticket not found with name " + ticketName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllTickets() {
        List<Ticket> tickets = registrationService.findAllTickets();
        Map<String, Object> response = new HashMap<>();
        if (!tickets.isEmpty()) {
            response.put("message", "All tickets retrieved successfully");
            response.put("tickets", tickets);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "No tickets found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/name/{ticketName}")
    public ResponseEntity<?> deleteTicketByName(@PathVariable String ticketName) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = registrationService.deleteByTicketName(ticketName);
        if (deleted) {
            response.put("message", "Ticket with name " + ticketName + " successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Ticket with name " + ticketName + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllTickets() {
        boolean deleted = registrationService.deleteAllTickets();
        if (deleted) {
            return ResponseEntity.ok("All tickets deleted successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No tickets found");
        }
    }

    @Data
    @ToString
    public static class TicketRequestBody {
        private String ticketName;
        private LocalDate ticketDate;
        private int earlyBirdTotal;
        private int earlyBirdRemain;
        private int earlyBirdPrice;
        private int regularTotal;
        private int regularRemain;
        private int regularPrice;
    }

}
