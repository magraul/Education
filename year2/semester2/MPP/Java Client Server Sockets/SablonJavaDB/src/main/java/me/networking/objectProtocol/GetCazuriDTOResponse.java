package me.networking.objectProtocol;

import me.networking.dto.CazCaritabilDTO;

import java.util.List;

public class GetCazuriDTOResponse implements Response {
    private List<CazCaritabilDTO> cazuri;

    public GetCazuriDTOResponse(List<CazCaritabilDTO> cazuri) {
        this.cazuri = cazuri;
    }

    public List<CazCaritabilDTO> getCazuri() {
        return cazuri;
    }
}
