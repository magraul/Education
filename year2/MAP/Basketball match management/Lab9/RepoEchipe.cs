using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace Lab9
{
    class RepoEchipe : AbstractFileRepository<string, Echipa>
    {
        public RepoEchipe(string fileName) : base(fileName, EntityToFileMapping.CreateEchipa)
        {
            populeaza();
        }

        private void populeaza()
        {

            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\date.txt");
            List<string> forSave = new List<string>();

            int id = 0;
            foreach (string linie in linies)
            {
                string[] elems = linie.Split(" - ");
                forSave.Add(id + "," + elems[1]);
                ++id;
            }

            File.WriteAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\echipe.txt", forSave);
           
        }

        internal string getIdDupaNume(string echipa)
        {
            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\echipe.txt");
            
            int id = 0;
            foreach (string linie in linies)
            {
                string[] elems = linie.Split(",");
                if (elems[1].Equals(echipa))
                    return elems[0];
            }
            return "";
        }

        public override string getLineFromEntity(Echipa e)
        {
            throw new NotImplementedException();
        }
    }
}
