package me.entities;

import java.io.Serializable;

public class Donator extends Person implements Serializable {

    public Donator(String name, String phoneNumber, String address) {
        super(name, phoneNumber, address);
    }
}
