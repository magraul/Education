using System;

namespace Lab9
{
    internal class ValidatorJucatori
    {
        internal void valideaza(Jucator jucator)
        {
            string errors = "";
            if (jucator.Nume.Equals(""))
                errors += "nume invalid!";
            if (jucator.Scoala.Equals(""))
                errors += "scoala invalida";
            if (errors.Length > 0)
                throw new Exception(errors);
        }
    }
}