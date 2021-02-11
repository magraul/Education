using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Lab9
{
    internal class Service
    {
        ValidatorMeciuri validatorMeciuri;
        ValidatorJucatoriActivi validatorJucatoriActivi;
        ValidatorJucatori validatorJucatori;
        ValidatorElevi validatorElevi;
        ValidatorEchipa validatorEchipe;
        RepoMeciuri repoMeciuri;
        RepoJucatoriActivi repoJucatoriActivi;
        RepoJucatori repoJucatori;
        RepoElevi repoElevi;
        RepoEchipe repoEchipe;

        public Service(RepoEchipe repoEchipe, RepoElevi repoElevi, RepoJucatori repoJucatori, RepoJucatoriActivi repoJucatoriActivi, RepoMeciuri repoMeciuri, ValidatorEchipa validatorEchipe, ValidatorElevi validatorElevi, ValidatorJucatori validatorJucatori, ValidatorJucatoriActivi validatorJucatoriActivi, ValidatorMeciuri validatorMeciuri)
        {
            this.validatorMeciuri = validatorMeciuri;
            this.validatorJucatoriActivi = validatorJucatoriActivi;
            this.validatorJucatori = validatorJucatori;
            this.validatorElevi = validatorElevi;
            this.validatorEchipe = validatorEchipe;
            this.repoMeciuri = repoMeciuri;
            this.repoJucatoriActivi = repoJucatoriActivi;
            this.repoJucatori = repoJucatori;
            this.repoElevi = repoElevi;
            this.repoEchipe = repoEchipe;
        }

        internal string getAllEchipe()
        {
            string rez = "";
            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\date.txt");

            foreach (string linie in linies)
            {
                string[] elems = linie.Split(" - ");
                    rez += elems[1] + "\n";
            }
            return rez;
        }

        internal string getAllJucatori()
        {
            var jucatori = repoJucatori.FindAll();
            string rez = "";
            foreach(var jucator in jucatori)
            {
                rez += jucator.Nume + "\n";
            }
            return rez;
        }

        internal void addJucatorActiv(string nume, string idMeci, string puncte, string tip)
        {
            string idJucator = repoJucatori.getIdByName(nume);
            JucatorActiv jucatorActiv = new JucatorActiv(idJucator, Int32.Parse(idMeci), Int32.Parse(puncte), tip);
            validatorJucatoriActivi.valideaza(jucatorActiv);
            repoJucatoriActivi.Save(jucatorActiv);
        }

        internal string getMeciurileEchipeiJucatorului(string nume)
        {
            string rez = "";
            Echipa echipa = repoJucatori.FindOne(repoJucatori.getIdByName(nume)).Echipa;
            foreach (var meci in repoMeciuri.FindAll())
                if (meci.Echipa1.Nume.Equals(echipa.Nume) || meci.Echipa2.Nume.Equals(echipa.Nume))
                    rez += meci.Id + " " + meci.Echipa1.Nume + " " + meci.Echipa2.Nume + "\n";
            return rez;
        }

        internal string getLinesMeciuri()
        {
            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\meciuri.txt");

            string rez = "";
          
            foreach (string linie in linies)
            {
                string[] elems = linie.Split(",");
                rez += elems[0] + " " + elems[1] + " " + elems[2] + "\n";
            }
            return rez;
        }

        public string echideleScolii(string idElev)
        {
            string scoala = repoElevi.FindOne(idElev).Scoala;
            string rez = "";

            string[] linies = File.ReadAllLines("E:\\An2 Sem 1\\MAP\\Lab9\\date.txt");
            
            foreach (string linie in linies)
            {
                string[] elems = linie.Split(" - ");
                if (elems[0].Equals(scoala))
                    rez += elems[1] + "\n";
            }
            return rez;

        }

      

      

      

       

        internal void addMeci(string echipa1, string echipa2, string data)
        {
            Echipa echipa_1 = repoEchipe.FindOne(repoEchipe.getIdDupaNume(echipa1));
            if (echipa_1 == null)
                throw new Exception("echipa 1 nu a fost gasita!");
            
            Echipa echipa_2 = repoEchipe.FindOne(repoEchipe.getIdDupaNume(echipa2));
            if (echipa_2 == null)
                throw new Exception("echipa 2 nu a fost gasita!");

            DateTime data_a = new DateTime();
            try
            {
                data_a = DateTime.Parse(data);
            } catch(Exception e)
            {
                throw new Exception("data invalida!");
            }
                Meci meci = new Meci(repoMeciuri.getNextId(), echipa_1, echipa_2, data_a);
            validatorMeciuri.valideaza(meci);
            repoMeciuri.Save(meci);
        }

        internal void addJucator(string idElev, string echipa)
        {
            Elev elev = repoElevi.FindOne(idElev);
            var a = repoEchipe.getIdDupaNume(echipa);
            Echipa e = repoEchipe.FindOne(repoEchipe.getIdDupaNume(echipa));

            Jucator jucator = new Jucator(e, elev.Id, elev.Nume, elev.Scoala);
            validatorJucatori.valideaza(jucator);
            repoJucatori.Save(jucator);
            
        }

        internal string GetJucatoriiEchipei(string echipa)
        {
            string rez = "Jucatorii echipei " + echipa + "sunt:\n";
            var jucatori = from jucator in repoJucatori.FindAll()
                           where jucator.Echipa.Nume.Equals(echipa)
                           select jucator;

            foreach (var jucator in jucatori)
                rez += jucator.Nume + "\n";
            return rez;

        }

        internal string raport2(string echipa, string idMeci)
        {
            string rez = "jucatorii activi de la meciul cu id " + idMeci + " din echipa " + echipa + "sunt:\n";
            var jucatoriActiviDinAcestMeci = from jucatorActiv in repoJucatoriActivi.FindAll()
                                             where jucatorActiv.IdMeci.ToString().Equals(idMeci)
                                             join jucatorNormal in repoJucatori.FindAll() on jucatorActiv.Id equals jucatorNormal.Id
                                             where jucatorNormal.Echipa.Nume.Equals(echipa)
                                             select jucatorActiv;
            foreach (var j in jucatoriActiviDinAcestMeci)
                rez += repoJucatori.FindOne(j.Id).Nume + "\n";

            return rez;



        }


        internal string meciuriInPerioada(DateTime data11, DateTime data22)
        {
            string rez = "";
            var meciuri = from meci in repoMeciuri.FindAll()
                          where meci.Data.CompareTo(data11) > 0 && meci.Data.CompareTo(data22) < 0
                          select meci;
            foreach (var mei in meciuri)
                rez += mei.Echipa1.Nume + " vs " + mei.Echipa2.Nume + "\n";
            return rez;
        }

        internal string getScorLaMeciul(string idMeci)
        {
            var JucatoriDinMeci = from jucatorActiv in repoJucatoriActivi.FindAll()
                                  where jucatorActiv.IdMeci.ToString().Equals(idMeci)
                                  join jucatorNormal in repoJucatori.FindAll() on jucatorActiv.Id equals jucatorNormal.Id
                                  select jucatorNormal;

            Meci meci = repoMeciuri.FindOne(idMeci);
            string ech1 = meci.Echipa1.Nume;
            string ech2 = meci.Echipa2.Nume;

            var JucatoriActiviDinEchipa1 = from jucator in JucatoriDinMeci
                                           where jucator.Echipa.Nume.Equals(ech1)
                                           join jucatorActiv in repoJucatoriActivi.FindAll() on jucator.Id equals jucatorActiv.Id
                                           select jucatorActiv;

            var JucatoriActiviDinEchipa2 = from jucator in JucatoriDinMeci
                                           where jucator.Echipa.Nume.Equals(ech2)
                                           join jucatorActiv in repoJucatoriActivi.FindAll() on jucator.Id equals jucatorActiv.Id
                                           select jucatorActiv;

            var scorEchipa1 = (from jucatoriECH1 in JucatoriActiviDinEchipa1
                               select jucatoriECH1.NrPuncteInscrise).Sum();

            var scorEchipa2 = (from jucatoriECH2 in JucatoriActiviDinEchipa2
                               select jucatoriECH2.NrPuncteInscrise).Sum();


            return "scorul este: " + scorEchipa1 + "----" + scorEchipa2;

        }
    }
}