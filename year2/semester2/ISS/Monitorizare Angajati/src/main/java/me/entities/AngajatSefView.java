package me.entities;

public class AngajatSefView {
    private String nume;
    private String oraSosire;
    public AngajatSefView(String nume, String oraSosire) {
        this.nume = nume;
        this.oraSosire = oraSosire;
    }

    public String getNume() {
        return nume;
    }

    public String getOraSosire() {
        return oraSosire;
    }
}
