using System;
using System.Collections.Generic;
using System.Text;
using System.IO;


namespace Lab9
{
    class RepoElevi : AbstractFileRepository<string, Elev>
    {
        public RepoElevi(string fileName) : base(fileName, EntityToFileMapping.CreateElev)
        {
            populeazaFisierulCuElevi();
        }

        private void populeazaFisierulCuElevi()
        {
            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\date.txt");

            File.WriteAllText("E:\\An2 Sem 1\\MAP\\Lab9\\elevi.txt", string.Empty);
            int index = 0;
            List<string> global = new List<string>();

            foreach (string linie in linies)
            {
                string[] elems = linie.Split(" - ");
                List<string> liniiDePusInFisier = genereazaLiniiPentru(elems[0], index);
                global.AddRange(liniiDePusInFisier);
                index += 15;
            }

            File.AppendAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\elevi.txt", global);
        }

        private List<string> genereazaLiniiPentru(string scoala, int index)
        {
            List<string> rez = new List<string>();

            int aux = index;
            int aux1 = index + 15;
            while (aux < aux1)
            {
                string temp = aux + "," + numeRandom() + "," + scoala;
                rez.Add(temp);
                ++aux;
            }

            return rez;

        }

        private string numeRandom()
        {
            Random r = new Random();
            string[] consonants = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "l", "n", "p", "q", "r", "s", "sh", "zh", "t", "v", "w", "x" };
            string[] vowels = { "a", "e", "i", "o", "u", "ae", "y" };
            string Name = "";
            Name += consonants[r.Next(consonants.Length)].ToUpper();
            Name += vowels[r.Next(vowels.Length)];
            int b = 2; //b tells how many times a new letter has been added. It's 2 right now because the first two letters are already in the name.
            while (b < 7)
            {
                Name += consonants[r.Next(consonants.Length)];
                b++;
                Name += vowels[r.Next(vowels.Length)];
                b++;
            }

            return Name;
        }

        public override string getLineFromEntity(Elev e)
        {
            throw new NotImplementedException();
        }
    }
}
