package me.teledonServices;

import me.networking.dto.CazCaritabilDTO;

import java.util.List;

public interface ITeledonObserver {
    void donationMade(List<String> donatori, List<CazCaritabilDTO> cazuri) throws TeledonExeption;
    void commitDonatori(List<String> donatori) throws TeledonExeption;
}
