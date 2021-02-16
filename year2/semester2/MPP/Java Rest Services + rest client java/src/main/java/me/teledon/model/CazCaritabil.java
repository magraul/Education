package me.teledon.model;

import java.io.Serializable;

public class CazCaritabil implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String description;

    public CazCaritabil() {
        this("");
    }

    public CazCaritabil(String description) {
        this.description = description;
    }
    public CazCaritabil(String id, String description){this.id = id;this.description = description;}

    public String getDescription() {
        return description;
    }
}
