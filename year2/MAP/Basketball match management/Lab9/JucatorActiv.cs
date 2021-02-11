using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{
    class JucatorActiv : Entity<string>
    {
        internal int IdMeci { get; set; }
        internal int NrPuncteInscrise { get; set; }
        internal string tip { get; set; }

        public JucatorActiv(string idJucator, int idMeci, int nrPuncteInscrise, string tip)
        {
            base.Id = idJucator;
            IdMeci = idMeci;
            NrPuncteInscrise = nrPuncteInscrise;
            this.tip = tip;
        }
    }
}
