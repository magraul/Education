using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{
    class Jucator : Elev
    {
        public Jucator(Echipa echipa, string id, string nume, string scoala) : base(id, nume, scoala)  
        {
            Echipa = echipa;
        }

        internal Echipa Echipa { get; set; }
    }
}
