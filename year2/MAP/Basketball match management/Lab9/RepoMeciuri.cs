using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{

    class RepoMeciuri : AbstractFileRepository<string, Meci>
    {
        public RepoMeciuri(string fileName) : base(fileName, EntityToFileMapping.CreateMeci)
        {

        }

        public override string getLineFromEntity(Meci e)
        {
            return e.Id + "," + e.Echipa1.Nume + "," + e.Echipa2.Nume + "," + e.Data.Date.ToString().Split(" ")[0];
        }

        internal string getNextId()
        {
            List<string> idUri = new List<string>(entities.Keys);
            List<int> ids = new List<int>();

            idUri.ForEach(id => ids.Add(Int32.Parse(id)));
            ids.Sort();
          
            int pozitie = 0;
            int pretendent = 1;
            for (; ; )
            {
                if (pretendent == ids.Count + 1)
                    break;
                if (ids[pozitie] != pretendent)
                    break;
                pozitie++;
                pretendent++;
            }

            return pretendent.ToString();
            
        }
    }
}
