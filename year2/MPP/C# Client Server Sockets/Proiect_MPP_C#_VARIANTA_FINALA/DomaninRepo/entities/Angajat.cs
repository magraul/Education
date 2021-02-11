using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.entities
{
    [Serializable]
    public class Angajat : Persoana
    {
        public string username { get; set; }
        public string password { get; set; }

        public Angajat(string name, string phoneNumber, string address, string username, string password) : base(name, phoneNumber, address)
        {
            this.password = password;
            this.username = username;
        }
    }
}
