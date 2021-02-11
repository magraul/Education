package me.networking.objectProtocol;

import me.entities.Angajat;

public class GetDonatoriRequest implements Request {
    private Angajat angajat;



    public Angajat getAngajat() {
        return angajat;
    }
}
