package me.ppd.server;

import me.ppd.server.model.Sala;
import me.ppd.server.model.Spectacol;
import me.ppd.server.model.Vanzare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
    public static final Integer nrMaximLocuri = 100;
    public static final Integer nrSpectacole = 3;
    public static final Integer nrClienti = 10;
    public static final Integer intervalVerificare = 5;

    public static void main(String[] args) {
        Sala sala = new Sala(nrMaximLocuri);
        adaugaSpectacole(sala);

        Rapoarte rapoarte = new Rapoarte();
        Server server = new Server(sala, rapoarte);

        List<Cumparare> clienti = new ArrayList<>();
        for (int i = 0; i < nrClienti; i++)
            clienti.add(new Cumparare(server));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

        Verificare verificare = new Verificare(sala, rapoarte);
        executor.scheduleAtFixedRate(verificare, 0, intervalVerificare, TimeUnit.SECONDS);
        clienti.forEach(x -> executor.scheduleAtFixedRate(x, 2, 2, TimeUnit.SECONDS));


        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        boolean inchis;
        try {
            inchis = executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        if (!inchis)
            System.out.println("Inchidere nereusita!");

        printOutput(sala, rapoarte);
    }

    private static void adaugaSpectacole(Sala s) {
        s.getSpectacole().add(new Spectacol(0, LocalDate.of(2021, 1, 8), "Spectacol 1", 100D));
        s.getSpectacole().add(new Spectacol(1, LocalDate.of(2021, 1, 9), "Spectacol 2", 200D));
        s.getSpectacole().add(new Spectacol(2, LocalDate.of(2021, 1, 10), "Spectacol 3", 300D));
    }

    private static void printOutput(Sala sala, Rapoarte rapoarte) {
        String finalNume = (System.currentTimeMillis() / 1000) + ".txt";

        StringBuilder sb = new StringBuilder();
        for (Spectacol s : sala.getSpectacole())
            sb.append(s.toString()).append("\n\n");

        try {
            Files.write(Paths.get("output/Spectacol" + finalNume), sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (Vanzare v : sala.getVanzari()) {
                Files.write(Paths.get("output/Vanzare" + finalNume), (v.toString() + "\n\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.write(Paths.get("output/Sala" + finalNume), (nrMaximLocuri + ",\n0,1,2,\n").getBytes());
            for (Spectacol s : sala.getSpectacole()) {
                for (Vanzare v : sala.getVanzari())
                    if (v.getIdSpectacol().equals(s.getIdSpectacol()))
                        Files.write(Paths.get("output/Sala" + finalNume),
                                (s.getIdSpectacol() + "," + v.getDataVanzare().format(dateTimeFormat) + ";").getBytes(),
                                StandardOpenOption.APPEND);
                Files.write(Paths.get("output/Sala" + finalNume), ("\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (String s : rapoarte.generareRaport()) {
                Files.write(Paths.get("output/Verificari" + finalNume), (s + "\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



