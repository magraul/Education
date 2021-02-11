package me.services;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.entities.*;
import me.events.EvenimentSchimbare;
import me.events.Event;
import me.events.TipEveniment;
import me.observer.Observable;
import me.observer.Observer;
import me.repositories.AngajatiRepository;
import me.repositories.SarciniRepository;
import me.validators.AngajatiValidator;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Service implements Observable<EvenimentSchimbare> {
    private List<Observer<EvenimentSchimbare>> observers = new ArrayList<>();
    private AngajatiRepository repositoryAngajati;
    private SarciniRepository sarciniRepository;
    private AngajatiValidator angajatiValidator;

    public Service(AngajatiRepository repositoryAngajati, SarciniRepository sarciniRepository, AngajatiValidator angajatiValidator) {
        this.repositoryAngajati = repositoryAngajati;
        this.angajatiValidator = angajatiValidator;
        this.sarciniRepository = sarciniRepository;
    }

    public boolean check(String username, String parola) {
        return repositoryAngajati.check(username, parola);
    }

    public List<AngajatDTO> getAllAngajati() {
        List<Cerere> cereri = getCereriActive();

        return repositoryAngajati.findAll().stream().map(a->{
            Cerere c = new Cerere();
            c.setIdPersoana(a.getId());
            AngajatDTO an = new AngajatDTO(a.getUsername(), a.getNume());
            if (cereri.contains(c))
            {
                an.setaCerut(true);
            }
            return an;
        }).collect(Collectors.toList());

    }

    public List<String> getListaNumeAngajati() {
        return getAllAngajati().stream().map(AngajatDTO::getNume).collect(Collectors.toList());
    }

    public void adaugaUser(String text, String text1, String text2, String text3, String text4) throws ValidationException {
        Angajat angajat = new Angajat();
        angajat.setUsername(text3);
        angajat.setTipPersoana(2);
        angajat.setStatusLogare(0);
        angajat.setParola(BCrypt.hashpw(text4,BCrypt.gensalt()));
        angajat.setOra_sosire("00:00");
        angajat.setNume(text);
        angajat.setNrTel(text2);
        angajat.setAdresa(text1);
        angajatiValidator.valideaza(angajat);
        Angajat a = repositoryAngajati.findByUser(angajat.getUsername());
        if (a != null)
            throw new ValidationException("user exista deja!");
        repositoryAngajati.save(angajat);
        notifyObservers(new EvenimentSchimbare(TipEveniment.USER_ADDED));
    }

    @Override
    public void addObserver(Observer<EvenimentSchimbare> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {

    }


    @Override
    public void notifyObservers(EvenimentSchimbare t) {
        observers.forEach(x->x.update(t));
    }

    public void deleteAngajat(String username) {
        Angajat a = repositoryAngajati.getByUsername(username);
        repositoryAngajati.delete(a.getId());
        notifyObservers(new EvenimentSchimbare(TipEveniment.USER_ADDED));
    }

    public Angajat getByUsername(String username) {
        return repositoryAngajati.getByUsername(username);
    }

    public void modificaUser(int id, String text, String text1, String text2, String text3, String text4) throws ValidationException {
        Angajat a = repositoryAngajati.get(id);
        a.setUsername(text3);
        a.setParola(BCrypt.hashpw(text4,BCrypt.gensalt()));
        a.setAdresa(text1);
        a.setNume(text);
        a.setNrTel(text2);
        angajatiValidator.valideaza(a);
        repositoryAngajati.update(a);
        notifyObservers(new EvenimentSchimbare(TipEveniment.USER_ADDED));
    }

    public List<Cerere> getCereriActive() {
        return repositoryAngajati.findAllCereriActive();
    }

    public void trimiteDate(Angajat a) {
        repositoryAngajati.dataSent(a);
        notifyObservers(new EvenimentSchimbare(TipEveniment.DATE_LOGARE));
    }

    public List<AngajatSefView> getAngajatiLogati() {
        List<Angajat> angajatiLogati = repositoryAngajati.getAngajatiLogati();
        return angajatiLogati.stream().map(x->new AngajatSefView(x.getNume(), x.getOra_sosire())).collect(Collectors.toList());
    }

    public List<SarcinaDTO> getSarciniDTO() {
        List<Sarcina> sarcini = sarciniRepository.findAll();
        return sarcini.stream().map(x-> new SarcinaDTO(x.getIdSarcina(), repositoryAngajati.get(x.getIdPersoana()).getNume(), x.getTitlu(), x.getDescriere(), x.getStatusSarcina(), x.getFeedback(), this)).collect(Collectors.toList());
    }

    public void deleteSarina(int idSarcina, String angajat) {
        Angajat angajatt = repositoryAngajati.getByName(angajat);
        Sarcina s = sarciniRepository.get(idSarcina);
        sarciniRepository.delete(s.getIdSarcina());
        notifyObservers(new EvenimentSchimbare(TipEveniment.SARCINA_DELETE, s.getIdPersoana(), s));
    }

    public int getIdPersoanaByNameAndOra(String nume, String oraSosire) {
        return repositoryAngajati.getByNumeAndOra(nume, oraSosire).getId();
    }

    public void addSarcina(int idPersoana, String titlu, String descriere) {
        Sarcina sarcina = new Sarcina();
        descriere = descriere.replaceAll("\n", " ");
        sarcina.setDescriere(descriere);
        sarcina.setIdPersoana(idPersoana);
        sarcina.setTitlu(titlu);
        sarcina.setStatusSarcina(0);
        sarcina.setFeedback("Nu este");
        sarciniRepository.save(sarcina);
        notifyObservers(new EvenimentSchimbare(TipEveniment.SARCINA_NOUA, idPersoana));
    }

    public Angajat getAngajat(String username) {
        return repositoryAngajati.getByUsername(username);
    }

    public boolean checkAngajat(String username, String parola) {
        Angajat a = repositoryAngajati.getByUsername(username);
        if (a == null) return false;
        return BCrypt.checkpw(parola, a.getParola());
    }

    public List<SarcinaDTO> getSarciniDTOAngajat(Angajat angajat) {

        return getSarciniDTO().stream().filter(x->x.getAngajat().equals(angajat.getNume())).collect(Collectors.toList());
    }

    public void finishSarcina(int idSarcina, String numeAngajat) {
        Angajat angajatt = repositoryAngajati.getByName(numeAngajat);
        Sarcina s = sarciniRepository.get(idSarcina);
        s.setStatusSarcina(1);
        sarciniRepository.update(s);
        notifyObservers(new EvenimentSchimbare(TipEveniment.SARCINA_COMPLETATA, angajatt));
    }

    public void addFeedbackSarcina(int idSarcina, String angajat) {
        notifyObservers(new EvenimentSchimbare(TipEveniment.CERERE_ADD_FEEDBACK, idSarcina, angajat));
    }

    public Sarcina getSarcina(int idSarcina) {
        return sarciniRepository.get(idSarcina);
    }

    public void feedBack(int idSarcina, String feedback) {
        Sarcina s = sarciniRepository.get(idSarcina);
        s.setFeedback(feedback);
        sarciniRepository.update(s);
        notifyObservers(new EvenimentSchimbare(TipEveniment.FEEDBACK_ADDED, s));
    }

    public void loadFormDescriereSarcina(int idSarcina, String angajat, String descriere) {
        notifyObservers(new EvenimentSchimbare(TipEveniment.LOAD_FORM_DESCRIERE, idSarcina, angajat, descriere));
    }

    public void updateSarcina(Sarcina sarcina) {
        sarciniRepository.update(sarcina);
        notifyObservers(new EvenimentSchimbare(TipEveniment.SARCINA_UPDATE, sarcina));
    }

    public String cerereDate(String username) {
       Angajat a = repositoryAngajati.getByUsername(username);
       String parola = geterareParola();
       a.setParola(BCrypt.hashpw(parola,BCrypt.gensalt()));
       repositoryAngajati.update(a);
       return parola;

    }

    private String geterareParola() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();

    }

    public void logareAngajat(Angajat angajat, String text) {
        angajat.setOra_sosire(text);
        angajat.setStatusLogare(1);
        repositoryAngajati.update(angajat);
        notifyObservers(new EvenimentSchimbare(TipEveniment.ANGAJAT_LOGAT));
    }

    public void plecareAngajat(Angajat angajat) {
        angajat.setStatusLogare(0);
        repositoryAngajati.update(angajat);
        notifyObservers(new EvenimentSchimbare(TipEveniment.ANGAJAT_LOGAT));
    }

    public Angajat getAngajatById(int idPersoana) {
        return repositoryAngajati.get(idPersoana);
    }
}
