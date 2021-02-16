package me.networking.dto;

import java.io.Serializable;

public class DonatieDTO implements Serializable {
    private Integer cazId;
    private String numeDonator, adresa, nrTel;
    private Float sumaDonata;

    public DonatieDTO(Integer cazId, String numeDonator, String adresa, String nrTel, Float sumaDonata) {
        this.cazId = cazId;
        this.numeDonator = numeDonator;
        this.adresa = adresa;
        this.nrTel = nrTel;
        this.sumaDonata = sumaDonata;
    }

    public Integer getCazId() {
        return cazId;
    }

    public String getNumeDonator() {
        return numeDonator;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getNrTel() {
        return nrTel;
    }

    public Float getSumaDonata() {
        return sumaDonata;
    }
}
