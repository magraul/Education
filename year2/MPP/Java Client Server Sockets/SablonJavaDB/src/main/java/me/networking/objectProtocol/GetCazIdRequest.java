package me.networking.objectProtocol;

import me.networking.dto.CazCaritabilDTO;

public class GetCazIdRequest implements Request{
    private CazCaritabilDTO cazCaritabilDTO;

    public GetCazIdRequest(CazCaritabilDTO cazCaritabilDTO) {
        this.cazCaritabilDTO = cazCaritabilDTO;
    }

    public CazCaritabilDTO getCazCaritabilDTO() {
        return cazCaritabilDTO;
    }
}
