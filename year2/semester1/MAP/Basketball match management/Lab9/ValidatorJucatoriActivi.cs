using System;

namespace Lab9
{
    internal class ValidatorJucatoriActivi
    {
        internal void valideaza(JucatorActiv jucatorActiv)
        {
            string errors = "";
            if (jucatorActiv.NrPuncteInscrise < 0)
                errors += "nr puncte invalid!";
            if (jucatorActiv.tip.Equals(""))
                errors += "tip invalid!";

            if (errors.Length > 0)
                throw new Exception(errors);

        }
    }
}