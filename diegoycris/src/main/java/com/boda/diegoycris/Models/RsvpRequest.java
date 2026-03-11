package com.boda.diegoycris.models;

public class RsvpRequest {//DTO Data Transfer Object para poder recibir los datos del cliente en el controller
    public String fullName;
    public Assist assist;

    public RsvpRequest() {
        this.fullName = "";
        this.assist = Assist.NO;
    }
    public RsvpRequest(String fullName, Assist assist) {
        this.fullName = fullName;
        this.assist = assist;
    }
    
}
