package me.networking.objectProtocol;

import java.util.List;

public class commitDonatoriResponse implements UpdateResponse {
    List<String> lis;

    public commitDonatoriResponse(List<String> lis) {
        this.lis = lis;
    }

    public List<String> getLis() {
        return lis;
    }
}
