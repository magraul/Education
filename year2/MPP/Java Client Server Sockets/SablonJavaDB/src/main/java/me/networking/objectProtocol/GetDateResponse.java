package me.networking.objectProtocol;

import me.networking.dto.CazCaritabilDTO;

import java.util.List;

public class GetDateResponse implements UpdateResponse {
    private List<CazCaritabilDTO> cazCaritabilDTOS;
    private List<String> donatori;

    public GetDateResponse(List<CazCaritabilDTO> cazCaritabilDTOS, List<String> donatori) {
        this.cazCaritabilDTOS = cazCaritabilDTOS;
        this.donatori = donatori;
    }

    public List<CazCaritabilDTO> getCazCaritabilDTOS() {
        return cazCaritabilDTOS;
    }

    public List<String> getDonatori() {
        return donatori;
    }
}
