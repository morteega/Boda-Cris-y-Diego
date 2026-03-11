package com.boda.diegoycris.models;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface RsvpRepository extends JpaRepository<Rsvp, Integer> {
    Optional<Rsvp> findByFullName(String fullName);
}
