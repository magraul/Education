package me.ppd.server;

import me.ppd.server.model.Vanzare;

import java.util.LinkedList;
import java.util.List;

public class Rapoarte {
    private final List<Vanzare> vanzariEsuate = new LinkedList<>();
    private final List<Verificare.Rezultat> rezultateVerificari = new LinkedList<>();

    public void adaugaVanzare(Vanzare v) {
        synchronized (vanzariEsuate) {
            vanzariEsuate.add(v);
        }
    }

    public void adaugaRezultatVerificare(Verificare.Rezultat rez) {
        synchronized (rezultateVerificari) {
            rezultateVerificari.add(rez);
        }
        System.out.println(rez.toString());
    }

    public List<String> generareRaport() {
        List<String> ret = new LinkedList<>();

        for(Verificare.Rezultat rez : rezultateVerificari)
            ret.add(rez.toString());

        ret.add("\n\nVanzari esuate:");
        for(Vanzare v : vanzariEsuate)
            ret.add(v.toString());

        return ret;
    }
}
