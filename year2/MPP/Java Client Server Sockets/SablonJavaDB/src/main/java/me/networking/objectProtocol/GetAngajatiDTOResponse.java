package me.networking.objectProtocol;

import me.networking.dto.AngajatDTO;

import java.util.List;

public class GetAngajatiDTOResponse implements Response {
    private List<AngajatDTO> angajati;

    public GetAngajatiDTOResponse(List<AngajatDTO> angajati) {
        this.angajati = angajati;
    }

    public List<AngajatDTO> getAngajati() {
        return angajati;
    }
}
