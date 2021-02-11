package me.ppd.server.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Spectacol {
    private Integer idSpectacol;
    private LocalDate data;
    private String titlu;
    private Double pretBilet;
    private List<Integer> locuriVandute;
    private Double sold;

    public Spectacol(Integer idSpectacol, LocalDate data, String titlu, Double pretBilet) {
        this.idSpectacol = idSpectacol;
        this.data = data;
        this.titlu = titlu;
        this.pretBilet = pretBilet;
        this.locuriVandute = new ArrayList<>();
        this.sold = 0D;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        locuriVandute.forEach(x -> sb.append(x).append(","));
        sb.setLength(sb.length() - 1);
        return idSpectacol + "; " + data.toString() + "; " + titlu + "; " + pretBilet + "\n" + sb.toString() + "\n" + sold;
    }

    public Integer getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(Integer idSpectacol) {
        this.idSpectacol = idSpectacol;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Double getPretBilet() {
        return pretBilet;
    }

    public void setPretBilet(Double pretBilet) {
        this.pretBilet = pretBilet;
    }

    public List<Integer> getLocuriVandute() {
        return locuriVandute;
    }

    public void setLocuriVandute(List<Integer> locuriVandute) {
        this.locuriVandute = locuriVandute;
    }

    public Double getSold() {
        return sold;
    }

    public void setSold(Double sold) {
        this.sold = sold;
    }
}
