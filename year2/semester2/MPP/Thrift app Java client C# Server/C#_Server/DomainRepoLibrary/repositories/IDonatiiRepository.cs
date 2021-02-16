using System;
using System.Collections.Generic;
using System.Text;

namespace Domain_Repo.repositories
{
    interface IDonatiiRepository : IRepository<Int32, entities.Donatie>
    {
        List<entities.Donatie> findDonatiiDupaSuma(float suma);
        List<entities.Donatie> findDonatiiDupaCaz(entities.CazCaritabil cazCaritabil);
        List<entities.Donatie> findDonatiiDupaDonator(entities.Donator donator);

    }
}
