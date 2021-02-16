package me.networking.objectProtocol;

import me.entities.Donator;

public class GetDonatorResponse implements Response {
    private Donator donator;

    public GetDonatorResponse(Donator donator) {
        this.donator = donator;
    }

    public Donator getDonator() {
        return donator;
    }
}
