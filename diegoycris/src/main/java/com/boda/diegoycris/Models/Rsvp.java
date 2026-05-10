package com.boda.diegoycris.models;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "assist")
    public Assist assist;
    @Column(name = "updated_at")
    private long updatedAt;
    @Column(name="intolerances")
    private String intolerances;

    public Rsvp() {}

    public Rsvp(String fullName, Assist assist, String intolerances) {
        this.fullName = fullName;
        this.assist = assist;
        this.intolerances = intolerances;
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
    public String getIntolerances() {
        return intolerances;
    }
    public void setIntolerances(String intolerances) {
        this.intolerances = intolerances;
    }

}
