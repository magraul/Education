using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.entities
{
    [Serializable]
    public abstract class Persoana : Entity<Int32>
    {
        public string Name { get; set; }
        public string PhoneNumber { get; set; }
        public string Address { get; set; }

        public Persoana(string Name, string PhoneNumber, string Address)
        {
            this.Address = Address;
            this.Name = Name;
            this.PhoneNumber = PhoneNumber;
        }
    }
}
