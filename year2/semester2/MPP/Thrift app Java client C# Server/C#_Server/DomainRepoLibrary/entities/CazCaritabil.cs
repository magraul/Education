using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.entities
{
    [Serializable]
    public class CazCaritabil : Entity<Int32>
    {
        public string description { get; set; }

        public CazCaritabil(string descr)
        {
            this.description = descr;
        }
    }
}
