package com.boda.diegoycris.Models;

public class RsvpRequest {//DTO Data Transfer Object para poder recibir los datos del cliente en el controller
    public String fullname;
    public Assist assist;

    public RsvpRequest() {
        this.fullname = "";
        this.assist = Assist.NO;
    }
    public RsvpRequest(String fullname, Assist assist) {
        this.fullname = fullname;
        this.assist = assist;
    }
    
}
