package me.ppd.server;

import me.ppd.server.model.Sala;
import me.ppd.server.model.Spectacol;
import me.ppd.server.model.Vanzare;

import java.time.LocalDateTime;
import java.util.List;

public class Server {
    private final Sala sala;
    private final Rapoarte rapoarte;

    public Server(Sala sala, Rapoarte rapoarte) {
        this.sala = sala;
        this.rapoarte = rapoarte;
    }

    public boolean cerereVanzare(int idSpectacol, List<Integer> locuri) {
        int nrBilete = locuri.size();

        Spectacol s = sala.getSpectacole().get(idSpectacol);
        double suma = s.getPretBilet() * nrBilete;

        boolean reusit = true;

        synchronized (s.getLocuriVandute()) {
            for (int loc : locuri) {
                if (s.getLocuriVandute().contains(loc)) {
                    reusit = false;
                    break;
                }
            }

            Vanzare vanzare = new Vanzare(idSpectacol, LocalDateTime.now(), nrBilete, locuri, suma);

            if (reusit) {
                s.getLocuriVandute().addAll(locuri);
                synchronized (s.getSold()) {
                    s.setSold(s.getSold() + suma);
                }
                synchronized (sala.getVanzari()) {
                    sala.getVanzari().add(vanzare);
                }
            } else
                rapoarte.adaugaVanzare(vanzare);
        }

        return reusit;
    }
}
