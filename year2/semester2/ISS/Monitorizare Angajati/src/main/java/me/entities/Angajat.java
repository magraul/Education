package me.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "persoanedinfirma")
public class Angajat implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nume")
    private String nume;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Column(name = "username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Column(name = "parola")
    private String parola;

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }


@Column(name = "adresa")
    private String adresa;

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }


    @Column(name = "numartelefon")
    private String nrTel;

    public String getNrTel() {
        return nrTel;
    }

    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
    }

    @Column(name = "tippersoana")
    private int tipPersoana;

    public int getTipPersoana() {
        return tipPersoana;
    }

    public void setTipPersoana(int tipPersoana) {
        this.tipPersoana = tipPersoana;
    }

    @Column(name = "statuslogare")
    private int statusLogare;

    public int getStatusLogare() {
        return statusLogare;
    }

    public void setStatusLogare(int statusLogare) {
        this.statusLogare = statusLogare;
    }

    @Column(name = "ora_sosire")
    private String ora_sosire;

    public String getOra_sosire() {
        return ora_sosire;
    }

    public void setOra_sosire(String ora_sosire) {
        this.ora_sosire = ora_sosire;
    }
}
