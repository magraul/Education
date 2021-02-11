package me.networking.objectProtocol;

import me.networking.dto.CazCaritabilDTO;

import java.util.List;

public class DonationMadeResponse implements UpdateResponse {
    private List<CazCaritabilDTO> cazuri;
    private List<String> randuri;

    public DonationMadeResponse(List<CazCaritabilDTO> cazuri, List<String> randuri) {
        this.cazuri = cazuri;
        this.randuri = randuri;
    }

    public List<CazCaritabilDTO> getCazuri() {
        return cazuri;
    }

    public List<String> getRanduri() {
        return randuri;
    }
}
