using System;
using System.Collections.Generic;
using System.Text;
using System.IO;

namespace Lab9
{
    class EntityToFileMapping
    {
        internal static Echipa CreateEchipa(string line)
        {
            string[] elems = line.Split(",");
            return new Echipa(elems[0], elems[1]);
        }

        internal static Meci CreateMeci(string line)
        {
            string[] elems = line.Split(",");

            return new Meci(elems[0], new Echipa(getIdEchipaByName(elems[1]), elems[1]), new Echipa(getIdEchipaByName(elems[2]), elems[2]), DateTime.Parse(elems[3]));
        }

        internal static Jucator CreateJucator(string line)
        {
            string[] elems = line.Split(",");
            return new Jucator(new Echipa(getIdEchipaByName(elems[3]), elems[3]), elems[0], elems[1], elems[2]);
            throw new NotImplementedException();
        }

      

        internal static Elev CreateElev(string line)
        {
            string[] elems = line.Split(",");
            return new Elev(elems[0], elems[1], elems[2]);
        }

        internal static JucatorActiv CreateJucatorActiv(string line)
        {
            string[] elems = line.Split(",");
            return new JucatorActiv(elems[0], Int32.Parse(elems[1]), Int32.Parse(elems[2]), elems[3]);
        }




        private static string getIdEchipaByName(string echipa)
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
    }
}
