package me.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sarcini")
public class Sarcina implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private int idSarcina;

    public int getIdSarcina() {
        return idSarcina;
    }

    public void setIdSarcina(int idSarcina) {
        this.idSarcina = idSarcina;
    }

    @Column(name = "idpersoana")
    private int idPersoana;

    public int getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(int idPersoana) {
        this.idPersoana = idPersoana;
    }

    @Column(name = "descriere")
    private String descriere;

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Column(name = "titlu")
    private String titlu;

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    @Column(name = "statussarcina")
    private int statusSarcina;

    public int getStatusSarcina() {
        return statusSarcina;
    }

    public void setStatusSarcina(int statusSarcina) {
        this.statusSarcina = statusSarcina;
    }

    @Column(name = "feedback")
    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
