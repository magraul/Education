package me.validators;

import me.entities.Donatie;
import org.controlsfx.validation.ValidationMessage;

import javax.xml.bind.ValidationException;

public class ValidatorDonatii {
    private String errors;
    public void valideaza(Donatie d) throws ValidationException {
        errors = "";
        if (d.getSumaDonata() <=0F)
            errors += "suma invalida!";
        if(errors.length() > 0)
            throw new ValidationException(errors);
    }
}
