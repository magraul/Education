package me.ppd.server;

import me.ppd.server.model.Sala;
import me.ppd.server.model.Spectacol;
import me.ppd.server.model.Vanzare;

import java.time.LocalDateTime;
import java.util.*;

public class Verificare implements Runnable {
    private final Sala sala;
    private final Rapoarte rapoarte;

    public Verificare(Sala sala, Rapoarte rapoarte) {
        this.sala = sala;
        this.rapoarte = rapoarte;
    }

    @Override
    public void run() {
//        System.out.println("verificare");
        HashMap<Integer, TreeSet<Integer>> locuriVandute = new HashMap<>();
        HashMap<Integer, Double> solduri = new HashMap<>();

        for(Spectacol s : sala.getSpectacole()) {
            locuriVandute.put(s.getIdSpectacol(), new TreeSet<>());
            solduri.put(s.getIdSpectacol(), 0D);
        }

        synchronized (sala.getVanzari()) {
            for (Vanzare v : sala.getVanzari()) {
                int id = v.getIdSpectacol();
                locuriVandute.get(id).addAll(v.getListaLocuriVandute());
                solduri.put(id, solduri.get(id) + v.getSuma());
            }
        }

        HashMap<Integer, RezultatSpectacol> rezultate = new HashMap<>();
        for(Spectacol s : sala.getSpectacole()) {
            boolean corect = true;
            int id = s.getIdSpectacol();
            TreeSet<Integer> locuriSpectacol = null;

            synchronized (s.getLocuriVandute()) {
                if (!s.getSold().equals(solduri.get(id)) //getLocuriVandute().size() * s.getPretBilet()
                        || s.getLocuriVandute().size() != locuriVandute.get(id).size()) {
                    //System.out.println("gresit1 " + s.getLocuriVandute().size() * s.getPretBilet() + " " + solduri.get(id) + "  " + s.getLocuriVandute().size() + " " + locuriVandute.get(id).size());
                    corect = false;
                }
                else
                    locuriSpectacol = new TreeSet<>(s.getLocuriVandute());
            }

            if(corect && !locuriSpectacol.equals(locuriVandute.get(id))) {
                //System.out.println("gresit2");
                corect = false;
            }

            rezultate.put(id, new RezultatSpectacol(corect, solduri.get(id), locuriVandute.get(id)));
        }
        rapoarte.adaugaRezultatVerificare(new Rezultat(LocalDateTime.now(), rezultate));
    }

    // Rezultatul verificarii pentru un singur spectacol
    private static class RezultatSpectacol {
        Boolean corect;
        Double sold;
        Collection<Integer> locuriVandute;

        public RezultatSpectacol(Boolean corect, Double sold, Collection<Integer> locuriVandute) {
            this.corect = corect;
            this.sold = sold;
            this.locuriVandute = locuriVandute;
        }
    }

    public static class Rezultat {
        private final LocalDateTime ora;
        private final HashMap<Integer, RezultatSpectacol> rezultateSpectacole;

        public Rezultat(LocalDateTime ora, HashMap<Integer, RezultatSpectacol> rezultateSpectacole) {
            this.ora = ora;
            this.rezultateSpectacole = rezultateSpectacole;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ora.format(Main.dateTimeFormat)).append("\n");
            rezultateSpectacole.forEach((k, v) -> {
                sb.append(k).append("; ").append(v.sold).append("; ").append(v.corect ? "corect" : "gresit").append("; ");
                v.locuriVandute.forEach(x -> sb.append(x).append(","));
                sb.setLength(sb.length() - 1);
                sb.append("\n");
            });
            return sb.toString();
        }
    }
}

