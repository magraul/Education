package me.ppd.server.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sala {
    private Integer nrLocuri;
    private List<Spectacol> spectacole;
    private List<Vanzare> vanzari;

    public Sala(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
        this.spectacole = new ArrayList<>();
        this.vanzari = new LinkedList<>();
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public List<Spectacol> getSpectacole() {
        return spectacole;
    }

    public void setSpectacole(List<Spectacol> spectacole) {
        this.spectacole = spectacole;
    }

    public List<Vanzare> getVanzari() {
        return vanzari;
    }

    public void setVanzari(List<Vanzare> vanzari) {
        this.vanzari = vanzari;
    }
}
