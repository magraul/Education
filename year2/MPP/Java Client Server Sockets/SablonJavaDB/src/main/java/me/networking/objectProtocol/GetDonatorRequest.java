package me.networking.objectProtocol;

public class GetDonatorRequest implements Request {
    private String nume;

    public GetDonatorRequest(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
}
