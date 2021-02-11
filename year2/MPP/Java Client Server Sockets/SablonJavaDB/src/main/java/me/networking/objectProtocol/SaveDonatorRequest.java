package me.networking.objectProtocol;

import me.entities.Donator;

public class SaveDonatorRequest implements Request{
    private Donator donator;

    public SaveDonatorRequest(Donator donator) {
        this.donator = donator;
    }

    public Donator getDonator() {
        return donator;
    }
}
