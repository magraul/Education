package me.teledonServer.utils;


import me.networking.objectProtocol.TeledonClientObjectWorker;
import me.teledonServices.ITeledonServices;

import java.net.Socket;

public class TeledonObjectConcurrentServer extends AbsConcurrentServer {
    private ITeledonServices service;
    public TeledonObjectConcurrentServer(int port, ITeledonServices service) {
        super(port);
        this.service = service;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        TeledonClientObjectWorker worker = new TeledonClientObjectWorker(service, client);
        Thread t = new Thread(worker);
        return t;
    }
}
