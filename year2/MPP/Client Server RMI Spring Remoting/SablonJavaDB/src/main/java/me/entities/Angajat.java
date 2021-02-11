package me.entities;

import java.io.Serializable;
import java.util.Objects;

public class Angajat extends Person implements Serializable, Comparable<Angajat> {
    private String username;
    private String password;


    public Angajat(String name, String phoneNumber, String address, String username, String password) {
        super(name, phoneNumber, address);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int compareTo(Angajat o) {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Angajat angajat = (Angajat) o;
        return Objects.equals(username, angajat.username) &&
                Objects.equals(password, angajat.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
