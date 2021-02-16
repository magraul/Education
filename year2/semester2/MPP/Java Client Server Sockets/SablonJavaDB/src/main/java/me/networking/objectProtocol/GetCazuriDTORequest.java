package me.networking.objectProtocol;

import me.entities.Angajat;

public class GetCazuriDTORequest implements Request{
    private Angajat angajat;



    public Angajat getAngajat() {
        return angajat;
    }
}
