package me.ppd.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientWorker implements Runnable {
    Connection con;

    public ClientWorker(Connection con) {
        this.con = con;
    }

    @Override
    public void run() {
        //System.out.println("cumparare");
        Random rand = new Random();

        int idSpectacol = rand.nextInt(3);
        int nrBilete = rand.nextInt(10) + 1;
        List<Integer> locuri = new ArrayList<>();

        while (locuri.size() < nrBilete) {
            int l = rand.nextInt(Main.nrMaximLocuri) + 1;
            if(!locuri.contains(l))
                locuri.add(l);
        }

        System.out.println("Cumparare: " + idSpectacol + " - " + locuri.toString());
        con.cerereVanzare(idSpectacol, locuri);
    }
}
