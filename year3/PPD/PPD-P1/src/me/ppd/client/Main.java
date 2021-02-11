package me.ppd.client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static ScheduledExecutorService executor;
    private static Connection connection;
    public static final Integer nrMaximLocuri = 100;
    private static HashMap<Integer, List<Integer>> locuriVandute;


    public static void main(String[] args) {
        locuriVandute = new HashMap<>();
        connection = new Connection();
        executor = Executors.newSingleThreadScheduledExecutor();

        try {
            connection.startConnection("localhost", 27015);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ClientWorker cw = new ClientWorker(connection);
        executor.scheduleAtFixedRate(cw, 0, 2, TimeUnit.SECONDS);
    }

    public static void adaugaLocuriVandute(int idSpectacol, List<Integer> locuri) {
        if (!locuriVandute.containsKey(idSpectacol))
            locuriVandute.put(idSpectacol, new ArrayList<>());

        locuriVandute.get(idSpectacol).addAll(locuri);
    }

    public static void stopClient() {
        executor.shutdown();
    }
}
