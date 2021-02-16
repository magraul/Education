package me.teledonServer;

import me.entities.*;
import me.networking.dto.AngajatDTO;
import me.networking.dto.CazCaritabilDTO;
import me.networking.dto.DonatieDTO;
import me.repositories.AngajatiDBRepository;
import me.repositories.CazuriDBRepository;
import me.repositories.DonatiiDBRepository;
import me.repositories.DonatoriDBRepository;
import me.teledonServices.ITeledonObserver;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;
import me.validators.ValidatorCazuri;
import me.validators.ValidatorDonatii;
import me.validators.ValidatorDonatori;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements ITeledonServices {
    private AngajatiDBRepository angajatiDBRepository;
    private CazuriDBRepository cazuriDBRepository;
    private DonatiiDBRepository donatiiDBRepository;
    private DonatoriDBRepository donatoriDBRepository;
    private ValidatorCazuri validatorCazuri;
    private ValidatorDonatii validatorDonatii;
    private ValidatorDonatori validatorDonatori;

    private Map<String, ITeledonObserver> loggedClients;

    public Service(AngajatiDBRepository angajatiDBRepository, CazuriDBRepository cazuriDBRepository, DonatiiDBRepository donatiiDBRepository, DonatoriDBRepository donatoriDBRepository, ValidatorCazuri validatorCazuri, ValidatorDonatii validatorDonatii, ValidatorDonatori validatorDonatori) {
        this.angajatiDBRepository = angajatiDBRepository;
        this.cazuriDBRepository = cazuriDBRepository;
        this.donatiiDBRepository = donatiiDBRepository;
        this.donatoriDBRepository = donatoriDBRepository;
        this.validatorCazuri = validatorCazuri;
        this.validatorDonatii = validatorDonatii;
        this.validatorDonatori = validatorDonatori;
        loggedClients = new ConcurrentHashMap<>();
    }

    // private List<Observer<EvenimentSchimbare>> observers = new ArrayList<>();


    public void donatie(Integer cazId, String numeDonator, String adresa, String nrTel, Float suma) throws ValidationException, TeledonExeption {
        Integer idDonator;
        if (donatoriDBRepository.findDonatoriDupaNume(numeDonator).size() != 0) {
            idDonator = donatoriDBRepository.findDonatoriDupaNume(numeDonator).get(0).getId();
            Donator d = new Donator(numeDonator, nrTel, adresa);
            d.setId(idDonator);
            donatoriDBRepository.update(d);
        } else {
            donatoriDBRepository.save(new Donator(numeDonator, nrTel, adresa));
            idDonator = donatoriDBRepository.findDonatoriDupaNume(numeDonator).get(0).getId();
        }


        Donatie d = new Donatie(suma, idDonator, cazId);
        validatorDonatii.valideaza(d);
        donatiiDBRepository.save(d);
        //notifyObservers(new EvenimentSchimbare(TipEveniment.DONATIE));
    }

    private void notifyAllAngajatiDonatie() throws TeledonExeption {
        System.out.println("in fct de notificare"
        );
        List<Angajat> angajatList = (List<Angajat>) angajatiDBRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadNo);
        System.out.println("angajati: " + angajatList);
        for (Angajat a : angajatList) {
            ITeledonObserver client = loggedClients.get(a.getUsername());
            System.out.println("clientul este " + client);
            if (client != null) {
                System.out.println("am intrat in clinet not null");
                System.out.println("se notifica " + client.toString());
                executor.execute(() -> {
                    try {
                        System.out.println("se notifica " + a.getUsername());
                        client.donationMade(getDonators(), getCauriDTO());
                    } catch (TeledonExeption e) {
                        System.out.println("error notify client");
                    }
                });
            }
        }
        executor.shutdown();
    }

    public List<Donator> cauta(String numeDonator) {
        return donatoriDBRepository.findDonatoriDupaNume(numeDonator);
    }


//    @Override
//    public void addObserver(Observer<EvenimentSchimbare> e) {
//        observers.add(e);
//    }
//
//    @Override
//    public void removeObserver(Observer<EvenimentSchimbare> e) {
//
//    }
//
//    @Override
//    public void notifyObservers(EvenimentSchimbare t) {
//        observers.stream().forEach(x -> x.update(t));
//    }

    public Map<String, String> getDataAutentificareAngajati() {
        Map<String, String> for_return = new HashMap<>();
        List<Angajat> angajati = (List<Angajat>) angajatiDBRepository.findAll();
        for (Angajat a : angajati) {
            for_return.put(a.getUsername(), a.getPassword());
        }

        return for_return;
    }

    public String encode(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt());
    }

    @Override
    public void updateAngajat(AngajatDTO i, String parola) {
        Angajat a = angajatiDBRepository.findAngajatiDupaNume(i.getNume()).get(0);
        a.setPassword(parola);
        a.setUsername(i.getUsername());
        angajatiDBRepository.update(a);
    }

    public List<AngajatDTO> getAllAngajati() {
        List<AngajatDTO> rez = new ArrayList<>();
        List<Angajat> angajatList = (List<Angajat>) angajatiDBRepository.findAll();
        for (Angajat a : angajatList) {
            AngajatDTO angajatDTO = new AngajatDTO(a.getName(), a.getUsername());
            rez.add(angajatDTO);
        }
        System.out.println(rez);
        return rez;
    }

    @Override
    public synchronized List<String> getListaNumeAngajati() {
        List<String> rez = new ArrayList<>();
        for (Angajat a : angajatiDBRepository.findAll()) {
            rez.add(a.getName());
        }
        return rez;
    }

    @Override
    public synchronized Integer getCazId(CazCaritabilDTO caz) {
        return cazuriDBRepository.findCazuriDupaDescriere(caz.getDescriere()).get(0).getId();
    }

    private Float getSumaAdunata(CazCaritabil c) {
        Float rez = 0F;
        for (Donatie d : donatiiDBRepository.findAll()) {
            if (d.getCazCaritabil().toString().equals(c.getId().toString())) {
                rez += d.getSumaDonata();
            }
        }
        return rez;
    }

    public List<CazCaritabilDTO> getAllCazuriDTO() {
        List<CazCaritabilDTO> rez = new ArrayList<>();
        List<CazCaritabil> cazCaritabilDTOS = (List<CazCaritabil>) cazuriDBRepository.findAll();
        for (CazCaritabil c : cazCaritabilDTOS) {
            CazCaritabilDTO cazCaritabilDTO = new CazCaritabilDTO(c.getDescription(), getSumaAdunata(c));
            rez.add(cazCaritabilDTO);
        }
        return rez;
    }

    public List<String> getListaNumeDonatori() {
        List<String> rez = new ArrayList<>();

        for (Donator d : donatoriDBRepository.findAll()) {
            rez.add(d.getName());
        }
        return rez;
    }

    public List<String> getRanduriDonatori() {
        List<String> rez = new ArrayList<>();
        for (Donator d : donatoriDBRepository.findAll()) {
            rez.add(d.getName() + "  " + d.getAddress() + "  " + d.getPhoneNumber());
        }

        return rez;
    }

    @Override
    public synchronized void login(Angajat angajat, ITeledonObserver client) throws TeledonExeption {
        if (angajat != null) {
            if (loggedClients.get(angajat.getUsername()) != null)
                throw new TeledonExeption("User already logged!");
            loggedClients.put(angajat.getUsername(), client);
        } else throw new TeledonExeption("Authentication failed.");
    }

//    @Override
//    public synchronized void donatieS(Integer cazId, String numeDonator, String adresa, String nrTel, Float sumaDonata) throws TeledonExeption {
//        try {
//            donatie(cazId, numeDonator, adresa, nrTel, sumaDonata);
//        } catch (ValidatorException e) {
//            e.printStackTrace();
//            throw new TeledonExeption(e.getMessage());
//        }
//        notifyAllAngajatiDonatie();
//    }

//    @Override
//    public synchronized void donatieS(Donatie donatie) throws TeledonExeption {
//        donatiiDBRepository.save(donatie);
//        notifyAllAngajatiDonatie();
//    }
//
//    @Override
//    public synchronized void donatieS(DonatieDTO donatieDTO) throws TeledonExeption {
////        try {
////            donatie(donatieDTO.getCazId(), donatieDTO.getNumeDonator(), donatieDTO.getAdresa(), donatieDTO.getNrTel(), donatieDTO.getSumaDonata());
////        } catch (ValidatorException e) {
////            e.printStackTrace();
////            throw new TeledonExeption(e.getMessage());
////        }
//
//        Integer idDonator;
//        if (donatoriDBRepository.findDonatoriDupaNume(donatieDTO.getNumeDonator()).size() != 0) {
//            idDonator = donatoriDBRepository.findDonatoriDupaNume(donatieDTO.getNumeDonator()).get(0).getId();
//            Donator d = new Donator(donatieDTO.getNumeDonator(), donatieDTO.getNrTel(), donatieDTO.getAdresa());
//            d.setId(idDonator);
//            donatoriDBRepository.update(d);
//        } else {
//            donatoriDBRepository.save(new Donator(donatieDTO.getNumeDonator(), donatieDTO.getNrTel(), donatieDTO.getAdresa()));
//            idDonator = donatoriDBRepository.findDonatoriDupaNume(donatieDTO.getNumeDonator()).get(0).getId();
//        }
//
//
//        Donatie d = new Donatie(donatieDTO.getSumaDonata(), idDonator, donatieDTO.getCazId());
//        try {
//            validatorDonatii.valideaza(d);
//        } catch (ValidatorException e) {
//            e.printStackTrace();
//        }
//        donatiiDBRepository.save(d);
//        notifyAllAngajatiDonatie();
//    }


    @Override
    public synchronized void donatieS(Float suma , Integer idDonator, Integer cazId) throws TeledonExeption {
        Donatie d = new Donatie(suma, idDonator, cazId);
        try {
            validatorDonatii.valideaza(d);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        donatiiDBRepository.save(d);
        notifyAllAngajatiDonatie();
    }

    @Override
    public void updateDonator(Donator donator) throws TeledonExeption {
        donatoriDBRepository.update(donator);
    }

    @Override
    public void saveDonator(Donator donator) throws TeledonExeption {
        donatoriDBRepository.save(donator);

    }

    @Override
    public void commitDonatori(String nume, String nrTel, String adresa) throws TeledonExeption {
        Donator donator = null;
              try {
                  donator = getDonator(nume);
                  if (donator != null && donator.getPhoneNumber().equals(nrTel)) {
                          Donator forUpdate = new Donator(nume, nrTel, adresa);
                          forUpdate.setId(donator.getId());
                          updateDonator(forUpdate);

                  } else {
                      saveDonator(new Donator(nume, nrTel, adresa));
                  }
              }catch (TeledonExeption e){
                  System.out.println("exceptie donator get server");
              }
              notifyAllAngajatiCommit();
    }

    @Override
    public void check(Angajat angajat) throws TeledonExeption {
        List<Angajat> angajatList = (List<Angajat>) angajatiDBRepository.findAll();
        boolean gasit = false;
        for (Angajat a:angajatList){
            if (a.getUsername().equals(angajat.getUsername()) && a.getPassword().equals(angajat.getPassword())) {
                gasit = true;
                break;
            }
        }
        if (!gasit)
            throw new TeledonExeption("Datele introduse nu sunt inregistrate!");
    }

    private void notifyAllAngajatiCommit() {
        System.out.println("in fct de commit"
        );
        List<Angajat> angajatList = (List<Angajat>) angajatiDBRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadNo);
        System.out.println("angajati: " + angajatList);
        for (Angajat a : angajatList) {
            ITeledonObserver client = loggedClients.get(a.getUsername());
            System.out.println("clientul este " + client);
            if (client != null) {
                System.out.println("am intrat in clinet not null");
                System.out.println("se notifica " + client.toString());
                executor.execute(() -> {
                    try {
                        System.out.println("se notifica " + a.getUsername());
                        client.commitDonatori(getDonators());
                    } catch (TeledonExeption e) {
                        System.out.println("error notify client");
                    }
                });
            }
        }
        executor.shutdown();
    }

    @Override
    public synchronized Donator getDonator(String nume) throws TeledonExeption {
        List<Donator> list = donatoriDBRepository.findDonatoriDupaNume(nume);
        if (list.size() == 0)
            return null;
        return list.get(0);
    }


    private final int defaultThreadNo = 5;


    //miercuri 12 1

    @Override
    public synchronized void logout(Angajat angajat, ITeledonObserver client) throws TeledonExeption {
        System.out.println("log out in service");
        System.out.println("se delogheaza " + angajat.getUsername());
        System.out.println("logati: " + loggedClients.keySet());
        ITeledonObserver localClient = loggedClients.remove(angajat.getUsername());
        if (localClient == null)
            throw new TeledonExeption("Angajat " + angajat.getUsername() + " nu e logat");
    }

    @Override
    public synchronized List<CazCaritabilDTO> getCauriDTO() throws TeledonExeption {
        return getAllCazuriDTO();
    }

    @Override
    public synchronized List<String> getDonators() throws TeledonExeption {
        return getRanduriDonatori();
    }

    @Override
    public synchronized List<AngajatDTO> getAngajati() throws TeledonExeption {
        return getAllAngajati();
    }
}
