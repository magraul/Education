package me.networking.objectProtocol;

import me.entities.Donatie;
import me.networking.dto.DonatieDTO;

public class DoDonationRequest implements Request{
    private Integer cazId, idDonator;
    private Float sumaDonata;


    public DoDonationRequest(Float sumaDonata, Integer idDonator, Integer cazId) {
        this.sumaDonata = sumaDonata;
        this.idDonator = idDonator;
        this.cazId = cazId;
    }

    public Integer getCazId() {
        return cazId;
    }

    public Integer getIdDonator() {
        return idDonator;
    }

    public Float getSumaDonata() {
        return sumaDonata;
    }
}
