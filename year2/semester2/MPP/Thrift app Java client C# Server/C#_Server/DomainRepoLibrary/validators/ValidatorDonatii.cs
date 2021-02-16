using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain_Repo.entities;

namespace DomainRepoLibrary.validators
{
    public class ValidatorDonatii
    {
        private string Errors;
        public void valideaza(Donatie d)
        {
            Errors = "";
        if (d.sumaDonata <= 0F)
            Errors += "suma invalida!";
            if (Errors.Length > 0)
                throw new Exception(Errors);
    }
}
}
