package me.networking.objectProtocol;

import me.networking.dto.AngajatDTO;

public class UpdateAngajatRequest implements Request {
    private AngajatDTO a;
    private String p;
    public UpdateAngajatRequest(AngajatDTO a, String parola) {
        this.a = a;
        this.p=parola;
    }

    public AngajatDTO getA() {
        return a;
    }

    public String getP() {
        return p;
    }
}
