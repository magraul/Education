package me.networking.objectProtocol;

public class commitDonatoriRequest implements Request {
    private String nume, nrTel, adresa;

    public commitDonatoriRequest(String nume, String nrTel, String adresa) {
        this.nume = nume;
        this.nrTel = nrTel;
        this.adresa = adresa;
    }

    public String getNume() {
        return nume;
    }

    public String getNrTel() {
        return nrTel;
    }

    public String getAdresa() {
        return adresa;
    }
}

