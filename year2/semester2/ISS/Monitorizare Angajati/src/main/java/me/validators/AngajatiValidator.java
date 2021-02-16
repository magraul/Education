package me.validators;

import me.entities.Angajat;

import javax.xml.bind.ValidationException;

public class AngajatiValidator {
    private String errors;
    public void valideaza(Angajat a) throws ValidationException {
            errors = "";
            if (a.getAdresa().equals(""))
                errors +="adresa invalida! ";
            if (a.getNrTel().equals(""))
                errors += "nr tel invalid";
            if (a.getNume().equals(""))
                errors+="nume invalid";
            if (a.getParola().equals(""))
                errors += "parola invalida";
            if (a.getUsername().equals(""))
                errors+="username invalid";
            if (0 > a.getTipPersoana() || 2 < a.getTipPersoana())
                errors +="tip persoana invalid";
            if (errors.length() > 0)
                throw new ValidationException(errors);
    }
}
