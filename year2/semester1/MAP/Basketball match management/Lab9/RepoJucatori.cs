using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace Lab9
{
    class RepoJucatori : AbstractFileRepository<string, Jucator>
    {
        public RepoJucatori(string fileName) : base(fileName, EntityToFileMapping.CreateJucator)
        {

        }

        public override string getLineFromEntity(Jucator e)
        {
            return e.Id + "," + e.Nume + "," + e.Scoala + "," + e.Echipa.Nume;
        }

        internal string getIdByName(string nume)
        {
            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\jucatori.txt");

           
            foreach (string linie in linies)
            {
                string[] elems = linie.Split(",");
                if (elems[1].Equals(nume))
                    return elems[0];
            }
            return "";
        }
    }
}
