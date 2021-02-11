package me.networking.objectProtocol;

import me.entities.Angajat;

public class CheckRequest implements Request {
    private Angajat angajat;
    public CheckRequest(Angajat angajat) {
        this.angajat = angajat;
    }

    public Angajat getAngajat() {
        return angajat;
    }
}
