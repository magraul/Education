package me.teledonServices;

public class TeledonExeption extends Exception{

    public TeledonExeption(String message){super(message);}

    public TeledonExeption(String message, Throwable cause){
        super(message,cause);
    }
}
