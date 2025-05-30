package ca.mcgillcssa.cssabackend.controller;

import ca.mcgillcssa.cssabackend.dto.TicketDTO;
import ca.mcgillcssa.cssabackend.dto.RegistrationDTO;
import ca.mcgillcssa.cssabackend.model.Ticket;
import ca.mcgillcssa.cssabackend.model.Registration;
import ca.mcgillcssa.cssabackend.model.Registration.RegistrationType;
import ca.mcgillcssa.cssabackend.service.RegistrationService;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    /*
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
    */
}
