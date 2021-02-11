using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.repositories
{
    interface ICazuriRepository : IRepository<Int32, entities.CazCaritabil>
    {
        List<entities.CazCaritabil> findCazuriDupaDescriere(String descriere);
    }
}
