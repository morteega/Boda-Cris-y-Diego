package com.boda.diegoycris.Models;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "rsvp")
public class Rsvp {
    @Column(name = "full_name")
    private String fullName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "assist")
    public Assist assist;
    @Column(name = "updated_at")
    private long updatedAt;
    
    public Rsvp() {}

    public Rsvp(String fullName, Assist assist) {
        this.fullName = fullName;
        this.assist = assist;
        Date upadated = new Date();
        this.updatedAt = upadated.getTime();
    }
    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Assist getAssist() {
        return assist;
    }
    public void setAssist(Assist assist){
        this.assist = assist;  
    }
    public long getUpdatedAt() {
        return updatedAt;
    }


}
