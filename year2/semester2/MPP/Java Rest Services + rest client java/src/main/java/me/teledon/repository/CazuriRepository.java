package me.teledon.repository;
import me.teledon.model.CazCaritabil;

import java.util.List;

public interface CazuriRepository extends Repository<String, CazCaritabil> {
    public List<CazCaritabil> findCazuriDupaDescriere(String descriere);
}
