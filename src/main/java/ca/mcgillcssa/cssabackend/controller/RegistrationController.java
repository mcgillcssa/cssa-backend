package ca.mcgillcssa.cssabackend.controller;

import ca.mcgillcssa.cssabackend.dto.RegistrationDTO;
import ca.mcgillcssa.cssabackend.model.Registration;
import ca.mcgillcssa.cssabackend.model.Registration.RegistrationType;
import ca.mcgillcssa.cssabackend.service.RegistrationService;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createRegistration(@RequestBody RegistrationRequestBody requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            Registration newRegistration = registrationService.createRegistration(
                requestBody.getName(),
                requestBody.getTicketName(),
                requestBody.getEmail(),
                requestBody.getWechatId(),
                requestBody.getRegistrationDate(),
                requestBody.getRegistrationType()
            );
            response.put("message", "Registration created");
            response.put("registration", new RegistrationDTO(newRegistration));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "Failed to create registration.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            response.put("message", "Failed to create registration. Invalid URL.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (DataAccessException e) {
            response.put("message", "Failed to create registration. Database access error.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findRegistrationByName(@PathVariable String name) {
        List<Registration> registrations = registrationService.findByName(name);
        Map<String, Object> response = new HashMap<>();
        if (!registrations.isEmpty()) {
            response.put("message", "Registrations found for " + name);
            response.put("registrations", registrations);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Registrations not found for " + name);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/ticketName/{ticketName}")
    public ResponseEntity<?> findRegistrationByTicketName(@PathVariable String ticketName) {
        List<Registration> registrations = registrationService.findRByTicketName(ticketName);
        Map<String, Object> response = new HashMap<>();
        if (!registrations.isEmpty()) {
            response.put("message", "Registrations found for ticket " + ticketName);
            response.put("registrations", registrations);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Registrations not found for ticket " + ticketName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/name/{name}/ticketName/{ticketName}")
    public ResponseEntity<?> findRegistrationByNameAndTicketName(@PathVariable String name, @PathVariable String ticketName) {
        Optional<Registration> optionalRegistration = registrationService.findByNameAndTicketName(name, ticketName);
        Map<String, Object> response = new HashMap<>();
        if (optionalRegistration.isPresent()) {
            Registration registration = optionalRegistration.get();
            response.put("message", "Registration found for " + name + " and ticket " + ticketName);
            response.put("registration", new RegistrationDTO(registration));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Registration not found for " + name + " and ticket " + ticketName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllRegistrations() {
        List<Registration> registrations = registrationService.findAllRegistrations();
        Map<String, Object> response = new HashMap<>();
        if (!registrations.isEmpty()) {
            response.put("message", "All registrations retrieved successfully");
            response.put("registrations", registrations);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "No registrations found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<?> deleteRegistrationByName(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = registrationService.deleteByName(name);
        if (deleted) {
            response.put("message", "Registration for " + name + " successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Registration for " + name + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/ticketName/{ticketName}")
    public ResponseEntity<?> deleteRegistrationByTicketName(@PathVariable String ticketName) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = registrationService.deleteRByTicketName(ticketName);
        if (deleted) {
            response.put("message", "Registration for ticket " + ticketName + " successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Registration for ticket " + ticketName + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/name/{name}/ticketName/{ticketName}")
    public ResponseEntity<?> deleteRegistrationByNameAndTicketName(@PathVariable String name, @PathVariable String ticketName) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = registrationService.deleteByNameAndTicketName(name, ticketName);
        if (deleted) {
            response.put("message", "Registration for " + name + " and ticket " + ticketName + " successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            response.put("message", "Registration for " + name + " and ticket " + ticketName + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllRegistrations() {
        boolean deleted = registrationService.deleteAllRegistrations();
        if (deleted) {
            return ResponseEntity.ok("All registrations deleted successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No registrations found");
        }
    }

    //Testing
    @PostMapping("/purchaseEarlyBird")
    public ResponseEntity<?> purchaseEarlyBird(@PathVariable String ticketName, @PathVariable String buyerName, @PathVariable String email, @PathVariable String wechatId, @PathVariable LocalDate registrationDate) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean result = registrationService.purchaseEarlyBird(ticketName, buyerName, email, wechatId, registrationDate);
            if (result) {
                response.put("message", "EarlyBird ticket purchased successfully.");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else {
                response.put("message", "EarlyBird ticket purchase failed.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", "EarlyBird ticket purchase failed.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            response.put("message", "EarlyBird ticket purchase failed.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (DataAccessException e) {
            response.put("message", "EarlyBird ticket purchase failed.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //Testing
    @PostMapping("/purchaseRegular")
    public ResponseEntity<?> purchaseRegular(@PathVariable String ticketName, @PathVariable String buyerName, @PathVariable String email, @PathVariable String wechatId, @PathVariable LocalDate registrationDate) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean result = registrationService.purchaseRegular(ticketName, buyerName, email, wechatId, registrationDate);
            if (result) {
                response.put("message", "Regular ticket purchased successfully.");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else {
                response.put("message", "Regular ticket purchase failed.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("message", "Regular ticket purchase failed.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IOException e) {
            response.put("message", "Regular ticket purchase failed.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (DataAccessException e) {
            response.put("message", "Regular ticket purchase failed.");
            response.put("errorDetails", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Data
    @ToString
    public static class RegistrationRequestBody {
        private String name;
        private String ticketName;
        private String email;
        private String wechatId;
        private LocalDate registrationDate;
        private RegistrationType registrationType;
    }

}
