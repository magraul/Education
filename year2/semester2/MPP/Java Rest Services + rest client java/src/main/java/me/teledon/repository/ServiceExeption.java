package me.teledon.repository;

public class ServiceExeption extends RuntimeException {
    public ServiceExeption(Exception e) {
        super(e);
    }

    public ServiceExeption(String message) {
        super(message);
    }
}
