using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{
    class Meci : Entity<string>
    {
        internal Echipa Echipa1 { get ; set; }
        internal Echipa Echipa2 { get; set; }
     
        internal DateTime Data { get; set; }
        public Meci(string id, Echipa echipa1, Echipa echipa2, DateTime data)
        {
            base.Id = id;
            Echipa1 = echipa1;
            Echipa2 = echipa2;
            Data = data;
        }
    }
}
