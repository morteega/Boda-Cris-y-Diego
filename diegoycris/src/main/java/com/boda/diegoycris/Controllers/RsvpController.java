package com.boda.diegoycris.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boda.diegoycris.models.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api")   
public class RsvpController {
    private final RsvpRepository rsvpRepository;

    public RsvpController(RsvpRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }
    @PostMapping("/rsvp")
    @ResponseStatus(HttpStatus.CREATED)
    public Rsvp createRsvp(@RequestBody RsvpRequest rsvpRequest) {
        if(rsvpRequest.fullName == null || rsvpRequest.fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if(rsvpRequest.assist == null) {
            throw new IllegalArgumentException("Assist is required");
        }
        Rsvp rsvp = new Rsvp(rsvpRequest.fullName.trim(), rsvpRequest.assist);
        return rsvpRepository.save(rsvp);
    }

    @GetMapping("/admin/rsvp")
    public List<Rsvp> getAllRsvps() {
        return rsvpRepository.findAll();
    }

}
