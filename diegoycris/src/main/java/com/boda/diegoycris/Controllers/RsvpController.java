package com.boda.diegoycris.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.boda.diegoycris.models.*;
import com.boda.diegoycris.services.RsvpCsvService;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api") 
@CrossOrigin(origins = {
    "http://localhost:5500",
    "http://127.0.0.1:5500",
    "https://sin-fondo-conjunto--monumental-pony-e908da.netlify.app/" 
})  
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
    @GetMapping("/admin/rsvp/csv")
    public ResponseEntity<Resource> descargarCsv() {
        File file = new File("respuestas-rsvp.csv");

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=respuestas-rsvp.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    @GetMapping("/admin/rsvp")
    public List<Rsvp> getAllRsvps() {
        return rsvpRepository.findAll();
    }

}
