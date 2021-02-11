package me.entities;

public class AngajatDTO {
    private String username, nume;
    private boolean aCerut = false;

    public boolean aCerut() {
        return aCerut;
    }

    public void setaCerut(boolean aCerut) {
        this.aCerut = aCerut;
    }

    public AngajatDTO(String username, String nume) {
        this.username = username;
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }


    public String getNume() {
        return nume;
    }
}
