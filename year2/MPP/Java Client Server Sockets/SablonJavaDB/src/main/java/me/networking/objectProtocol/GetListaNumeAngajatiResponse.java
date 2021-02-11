package me.networking.objectProtocol;

import java.util.List;

public class GetListaNumeAngajatiResponse implements Response {
    private List<String> nume;

    public GetListaNumeAngajatiResponse(List<String> nume) {
        this.nume = nume;
    }

    public List<String> getNume() {
        return nume;
    }
}
