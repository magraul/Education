using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace Lab9
{
    class Elev : Entity<string>
    {
        public string Nume { get; set; }
        public string Scoala { get; set; }

        public Elev(string id, string nume, string scoala)
        {
            base.Id = id;
            Nume = nume;
            Scoala = scoala;

           
        }

       
    }
}
