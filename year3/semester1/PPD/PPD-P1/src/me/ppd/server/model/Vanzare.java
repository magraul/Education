package me.ppd.server.model;

import me.ppd.server.Main;

import java.time.LocalDateTime;
import java.util.List;

public class Vanzare {
    private Integer idSpectacol;
    private LocalDateTime dataVanzare;
    private Integer nrBileteVandute;
    private List<Integer> listaLocuriVandute;
    private Double suma;

    public Vanzare(Integer idSpectacol, LocalDateTime dataVanzare, Integer nrBileteVandute, List<Integer> listaLocuriVandute, Double suma) {
        this.idSpectacol = idSpectacol;
        this.dataVanzare = dataVanzare;
        this.nrBileteVandute = nrBileteVandute;
        this.listaLocuriVandute = listaLocuriVandute;
        this.suma = suma;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        listaLocuriVandute.forEach(x -> sb.append(x).append(","));
        sb.setLength(sb.length() - 1);
        return idSpectacol + "; " + dataVanzare.format(Main.dateTimeFormat) + "; " + nrBileteVandute + "\n" + sb.toString() + "\n" + suma;
    }

    public Integer getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(Integer idSpectacol) {
        this.idSpectacol = idSpectacol;
    }

    public LocalDateTime getDataVanzare() {
        return dataVanzare;
    }

    public void setDataVanzare(LocalDateTime dataVanzare) {
        this.dataVanzare = dataVanzare;
    }

    public Integer getNrBileteVandute() {
        return nrBileteVandute;
    }

    public void setNrBileteVandute(Integer nrBileteVandute) {
        this.nrBileteVandute = nrBileteVandute;
    }

    public List<Integer> getListaLocuriVandute() {
        return listaLocuriVandute;
    }

    public void setListaLocuriVandute(List<Integer> listaLocuriVandute) {
        this.listaLocuriVandute = listaLocuriVandute;
    }

    public Double getSuma() {
        return suma;
    }

    public void setSuma(Double suma) {
        this.suma = suma;
    }
}
