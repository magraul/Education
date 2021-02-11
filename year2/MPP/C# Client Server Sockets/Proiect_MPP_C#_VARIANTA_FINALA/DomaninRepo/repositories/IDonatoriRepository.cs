using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.repositories
{
    interface IDonatoriRepository : IRepository<Int32, entities.Donator>
    {
        IList<entities.Donator> findDonatoriDupaNume(string nume);
        IList<entities.Donator> findDonatoriDupaAdresa(string adresa);
    }
}
