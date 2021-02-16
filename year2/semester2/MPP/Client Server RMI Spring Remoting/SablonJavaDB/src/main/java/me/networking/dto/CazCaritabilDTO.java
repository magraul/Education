package me.networking.dto;

import java.io.Serializable;

public class CazCaritabilDTO implements Serializable {
    private String descriere;
    private Float sumaAdunata;

    public CazCaritabilDTO(String descriere, Float sumaAdunata) {
        this.descriere = descriere;
        this.sumaAdunata = sumaAdunata;
    }

    public String getDescriere() {
        return descriere;
    }

    public Float getSumaAdunata() {
        return sumaAdunata;
    }

    public void setSumaAdunata(Float sumaAdunata) {
        this.sumaAdunata = sumaAdunata;
    }
}
