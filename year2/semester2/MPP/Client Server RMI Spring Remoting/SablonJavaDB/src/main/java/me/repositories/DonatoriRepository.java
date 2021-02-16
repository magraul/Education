package me.repositories;

import me.entities.Donator;

import java.util.List;

public interface DonatoriRepository extends Repository<Integer, Donator> {
    public List<Donator> findDonatoriDupaNume(String nume ,String nrTel);
    public List<Donator> findDonatoriDupaAdresa(String adresa);
}
