package me.events;

import me.entities.Angajat;
import me.entities.Sarcina;

public class EvenimentSchimbare implements Event {
    private TipEveniment tip;
    private int idPersoana;
    private Angajat angajat;
    private int idSarcina;
    private String numeAngajat;
    private String descriere;
    private Sarcina sarcina;

    public EvenimentSchimbare(TipEveniment tip, Sarcina sarcina) {
        this.tip = tip;
        this.sarcina = sarcina;
    }

    public String getDescriere() {
        return descriere;
    }

    public Sarcina getSarcina() {
        return sarcina;
    }

    public EvenimentSchimbare(TipEveniment tip, int idSarcina, String numeAngajat) {
        this.tip = tip;
        this.idSarcina = idSarcina;
        this.numeAngajat = numeAngajat;
    }

    public EvenimentSchimbare(TipEveniment tip, int idSarcina, String angajat, String descriere) {
        this.tip = tip;
        this.idSarcina = idSarcina;
        this.numeAngajat = angajat;
        this.descriere = descriere;
    }

    public EvenimentSchimbare(TipEveniment tip, int idPersoana, Sarcina sarcina) {
        this.tip = tip;
        this.idPersoana = idPersoana;
        this.sarcina = sarcina;
    }


    public int getIdSarcina() {
        return idSarcina;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public EvenimentSchimbare(TipEveniment tip, Angajat angajat) {
        this.tip = tip;
        this.angajat = angajat;
    }

    public Angajat getAngajat() {
        return angajat;
    }

    public EvenimentSchimbare(TipEveniment tip) {
        this.tip = tip;
    }

    public EvenimentSchimbare(TipEveniment tip, int idPersoana) {
        this.tip = tip;
        this.idPersoana = idPersoana;
    }

    public int getIdPersoana() {
        return idPersoana;
    }

    public TipEveniment getTip() {
        return this.tip;
    }
}
