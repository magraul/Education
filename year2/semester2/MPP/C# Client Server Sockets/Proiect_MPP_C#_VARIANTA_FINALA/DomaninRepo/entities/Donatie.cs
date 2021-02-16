using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.entities
{
    [Serializable]
    public class Donatie : Entity<Int32>
    {
        public float sumaDonata { get; set; }
        public Int32 idDonator { get; set; }
        public Int32 idCazCaritabil { get; set; }

        public Donatie(float suma, Int32 idDonator, Int32 idCaz)
        {
            this.sumaDonata = suma;
            this.idDonator = idDonator;
            this.idCazCaritabil = idCaz;
        }
    
    }
}
