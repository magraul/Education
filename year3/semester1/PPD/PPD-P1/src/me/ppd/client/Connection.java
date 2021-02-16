package me.ppd.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection {
    private Socket clientSocket;
    private PrintWriter out;
    private Thread listener;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        listener = new Thread(() -> {
            for(;;) {
                String message;
                try {
                    message = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                if(message.equals("server-stopped")) {
                    Main.stopClient();

                    try {
                        in.close();
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }

                receiveMessage(message);
            }
        });
        listener.start();

    }

    private void receiveMessage(String msg) {
        if(msg.equals("success"))
            System.out.println("Cumparare reusita!");
        else if(msg.startsWith("sold")) {
            // "sold;id_spect;1,3,6,7"
            String[] parts = msg.split(";");

            int idSpectacol;
            List<Integer> locuri = new ArrayList<>();

            try {
                idSpectacol = Integer.parseInt(parts[1]);
                for(String loc : parts[2].split(","))
                    locuri.add(Integer.parseInt(loc));
            } catch (NumberFormatException ex) {
                System.out.println("Mesaj invalid.");
                return;
            }
            Main.adaugaLocuriVandute(idSpectacol, locuri);
        } else {
            System.out.println("Eroare la primirea datelor, primit: " + msg);
        }
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public void cerereVanzare(int idSpectacol, List<Integer> locuri) {
        StringBuilder sb = new StringBuilder();
        sb.append(idSpectacol).append(";");

        locuri.forEach(x -> sb.append(x).append(","));
        sb.setLength(sb.length() - 1);

        try {
            sendMessage(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



