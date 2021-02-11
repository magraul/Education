package me.networking.objectProtocol;

public class GetCazIdResponse implements Response {
    private Integer id;

    public GetCazIdResponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
