package me.networking.objectProtocol;

import me.entities.Donator;

public class UpdateDonatorRequest implements Request {
    private Donator donator;

    public UpdateDonatorRequest(Donator donator) {
        this.donator = donator;
    }

    public Donator getDonator() {
        return donator;
    }
}
