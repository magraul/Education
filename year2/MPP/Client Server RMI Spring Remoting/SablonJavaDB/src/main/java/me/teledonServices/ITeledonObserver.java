package me.teledonServices;

import me.entities.CazCaritabil;
import me.entities.Donator;
import me.networking.dto.CazCaritabilDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITeledonObserver extends Remote {
    void donationMade(CazCaritabilDTO caz) throws TeledonExeption, RemoteException;
    void donatorAdd(String d) throws TeledonExeption, RemoteException;
    void donatorUpdate(Donator d) throws TeledonExeption, RemoteException;
}
