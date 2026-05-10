package com.boda.diegoycris.models;

public class RsvpRequest {//DTO Data Transfer Object para poder recibir los datos del cliente en el controller
    public String fullName;
    public Assist assist;
    public String intolerances;

    public RsvpRequest() {
        this.fullName = "";
        this.assist = Assist.NO;
        this.intolerances = "";
    }
    public RsvpRequest(String fullName, Assist assist, String intolerances) {
        this.fullName = fullName;
        this.assist = assist;
        this.intolerances = intolerances;
    }
    
}
