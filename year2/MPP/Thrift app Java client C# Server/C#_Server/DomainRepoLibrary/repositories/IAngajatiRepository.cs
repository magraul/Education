using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.repositories
{
    interface IAngajatiRepository : IRepository<Int32, entities.Angajat>
    {
        List<entities.Angajat> findAngajatiDupaNume(string nume);
        List<entities.Angajat> findAngajatiDupaUsername(string nume);
    }
}
