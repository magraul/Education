package me.networking.dto;

import java.io.Serializable;

public class AngajatDTO implements Serializable {
    private String nume, username;

    public AngajatDTO(String nume, String username) {
        this.nume = nume;
        this.username = username;
    }

    public String getNume() {
        return nume;
    }

    public String getUsername() {
        return username;
    }
}

