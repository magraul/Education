package me.teledonServices;

import me.entities.Angajat;
import me.entities.Donator;
import me.networking.dto.AngajatDTO;
import me.networking.dto.CazCaritabilDTO;
import me.entities.Donatie;
import me.networking.dto.DonatieDTO;

import java.util.List;

public interface ITeledonServices {
    void login(Angajat angajat, ITeledonObserver client) throws TeledonExeption;
    //void donatieS(Integer cazId, String numeDonator, String adresa, String nrTel, Float sumaDonata) throws TeledonExeption;
    //void donatieS(Donatie donatie) throws TeledonExeption;
    //void donatieS(DonatieDTO donatie) throws TeledonExeption;
    void donatieS(Float sumaDonata, Integer idDonator, Integer cazId) throws TeledonExeption;
    void updateDonator(Donator donator) throws TeledonExeption;
    void saveDonator(Donator donator) throws TeledonExeption;
    void commitDonatori(String nume, String nrTel, String adresa) throws TeledonExeption;
    void check(Angajat angajat) throws TeledonExeption;
    Donator getDonator(String nume) throws TeledonExeption;
    void logout(Angajat angajat, ITeledonObserver client) throws TeledonExeption;
    List<CazCaritabilDTO> getCauriDTO() throws TeledonExeption;
    List<String> getDonators() throws TeledonExeption;
    List<AngajatDTO> getAngajati() throws TeledonExeption;
    List<String> getListaNumeAngajati() throws TeledonExeption;
    void updateAngajat(AngajatDTO a, String parola) throws TeledonExeption;
    Integer getCazId(CazCaritabilDTO cazCaritabilDTO) throws TeledonExeption;
}
