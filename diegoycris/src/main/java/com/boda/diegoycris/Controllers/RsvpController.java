package com.boda.diegoycris.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boda.diegoycris.models.*;
import com.boda.diegoycris.services.RsvpCsvService;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class RsvpController {
    private final RsvpRepository rsvpRepository;
    private final RsvpCsvService rsvpCsvService;

    public RsvpController(RsvpRepository rsvpRepository, RsvpCsvService rsvpCsvService) {
        this.rsvpRepository = rsvpRepository;
        this.rsvpCsvService = rsvpCsvService;
    }
    @PostMapping("/rsvp")
    @ResponseStatus(HttpStatus.CREATED)
    public Rsvp createRsvp(@RequestBody RsvpRequest rsvpRequest) {
        if(rsvpRequest.fullName == null || rsvpRequest.fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if(rsvpRequest.intolerances == null || rsvpRequest.intolerances.isEmpty()) {
            rsvpRequest.intolerances = "";
        }
        if(rsvpRequest.assist == null) {
            throw new IllegalArgumentException("Assist is required");
        }
        Rsvp rsvp = new Rsvp(rsvpRequest.fullName.trim(), rsvpRequest.assist, rsvpRequest.intolerances);
        Rsvp savedRsvp = rsvpRepository.save(rsvp);
        rsvpCsvService.guardarRsvp(savedRsvp);
        return savedRsvp;
    }

    @GetMapping("/rsvp/export")
    public ResponseEntity<Resource> export(@RequestParam String token) {
        if (!token.equals(System.getenv("EXPORT_TOKEN"))) {
            return ResponseEntity.status(403).build();
        }
        Path path = Path.of("respuestas-rsvp.csv");
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rsvp.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(path));
    }

}
