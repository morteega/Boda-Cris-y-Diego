package com.boda.diegoycris.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import java.util.List;
import com.boda.diegoycris.Models.*;

@RestController
@RequestMapping("/api")   
public class RsvpController {
    private final RsvpRepository rsvpRepository;

    public RsvpController(RsvpRepository rsvpRepository) {
        this.rsvpRepository = rsvpRepository;
    }
    @PostMapping("/rsvp")
    @ResponseStatus(HttpStatus.CREATED)
    public Rsvp createRsvp(RsvpRequest rsvpRequest) {
        if(rsvpRequest.fullname == null || rsvpRequest.fullname.isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if(rsvpRequest.assist == null) {
            throw new IllegalArgumentException("Assist is required");
        }
        Rsvp rsvp = new Rsvp(rsvpRequest.fullname.trim(), rsvpRequest.assist);
        return rsvpRepository.save(rsvp);
    }

    @GetMapping("/api/admin/rsvp")
    public List<Rsvp> getAllRsvps() {
        return rsvpRepository.findAll();
    }

}
