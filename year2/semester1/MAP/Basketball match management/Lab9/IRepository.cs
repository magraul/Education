using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{
    interface IRepository<TIP_ID, TIP_ENTITATE> where TIP_ENTITATE : Entity<TIP_ID>
    {
        TIP_ENTITATE FindOne(TIP_ID id);
        IEnumerable<TIP_ENTITATE> FindAll();
        TIP_ENTITATE Save(TIP_ENTITATE entity);
        TIP_ENTITATE Delete(TIP_ID id);
        TIP_ENTITATE Update(TIP_ENTITATE entity);
    }
}
