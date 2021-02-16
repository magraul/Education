using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.entities
{
    [Serializable]
    public class Donator : Persoana
    {
        public Donator(string name, string phoneNumber, string address) : base(name, phoneNumber, address)
        {
            
        }
    }
}
