using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.entities
{
    [Serializable]
    public abstract class Entity<TIP_ID>
    {
        public TIP_ID Id { get; set; }
    }
}
