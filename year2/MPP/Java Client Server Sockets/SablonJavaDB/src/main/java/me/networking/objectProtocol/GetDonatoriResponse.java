package me.networking.objectProtocol;

import me.entities.Donator;

import java.util.List;

public class GetDonatoriResponse implements Response {
    private List<String> randuri;

    public GetDonatoriResponse(List<String> randuri) {
        this.randuri = randuri;
    }

    public List<String> getRanduri() {
        return randuri;
    }
}
