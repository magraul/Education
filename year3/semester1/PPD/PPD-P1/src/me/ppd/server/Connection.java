package me.ppd.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection {
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        executor = Executors.newFixedThreadPool(4);

        while (true) {
            executor.submit(new Worker(serverSocket.accept()));
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class Worker implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public Worker(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                out.println(inputLine);
            }

            in.close();
            out.close();
            clientSocket.close();
        }
}
