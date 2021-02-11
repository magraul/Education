using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain_Repo.repositories;
using Domain_Repo.entities;
using DomainRepoLibrary.validators;
using DomainRepoLibrary.entities;
using servicess;

namespace Server
{
    public class Service : ITeledonServices
    {
        private readonly IDictionary<string, ITeledonObserver> loggedClients;

        private AngajatiDBRepository angajatiDBRepository;
        private CazuriDBRepository cazuriDBRepository;
        private DonatiiDBRepository donatiiDBRepository;
        private DonatoriDBRepository donatoriDBRepository;
        private ValidatorCazuri validatorCazuri;
        private ValidatorDonatii validatorDonatii;
        private ValidatorDonator validatorDonatori;

        public Service(AngajatiDBRepository angajatiDBRepository, CazuriDBRepository cazuriDBRepository, DonatiiDBRepository donatiiDBRepository, DonatoriDBRepository donatoriDBRepository, ValidatorCazuri validatorCazuri, ValidatorDonatii validatorDonatii, ValidatorDonator validatorDonatori)
        {
            this.angajatiDBRepository = angajatiDBRepository;
            this.cazuriDBRepository = cazuriDBRepository;
            this.donatiiDBRepository = donatiiDBRepository;
            this.donatoriDBRepository = donatoriDBRepository;
            this.validatorCazuri = validatorCazuri;
            this.validatorDonatii = validatorDonatii;
            this.validatorDonatori = validatorDonatori;
            loggedClients = new Dictionary<string, ITeledonObserver>();
        }



        /*  public void donatie(Int32 cazId, string numeDonator, string adresa, string nrTel, float suma)
          {
              int idDonator;
              if (donatoriDBRepository.findDonatoriDupaNume(numeDonator).Count != 0)
              {
                  idDonator = donatoriDBRepository.findDonatoriDupaNume(numeDonator)[0].Id;
                  Donator dd = new Donator(numeDonator, nrTel, adresa);
                  dd.Id = idDonator;
                  donatoriDBRepository.update(dd);
              }
              else
              {
                  donatoriDBRepository.save(new Donator(numeDonator, nrTel, adresa));
                  idDonator = donatoriDBRepository.findDonatoriDupaNume(numeDonator)[0].Id;
              }


              Donatie d = new Donatie(suma, idDonator, cazId);
              validatorDonatii.valideaza(d);
              donatiiDBRepository.save(d);

          }

          public List<Donator> cauta(string numeDonator)
          {
              return donatoriDBRepository.findDonatoriDupaNume(numeDonator).ToList();
          }


          public Dictionary<string, string> getDataAutentificareAngajati()
          {
              Dictionary<string, string> for_return = new Dictionary<string, string>();
              List<Angajat> angajati = (List<Angajat>)angajatiDBRepository.findAll();
              foreach (Angajat a in angajati)
              {
                  for_return.Add(a.username, a.password);
              }

              return for_return;
          }

          public string encode(string text)
          {
              return text;
              //return BCrypt.hashpw(text, BCrypt.gensalt());
          }*/

        /*public void updateAngajat(AngajatDTO i, String parola)
        {
            Angajat a = angajatiDBRepository.findAngajatiDupaNume(i.getNume()).get(0);
            a.setPassword(parola);
            a.setUsername(i.getUsername());
            angajatiDBRepository.update(a);
        }*/

        /*public List<AngajatDTO> getAllAngajati()
        {
            List<AngajatDTO> rez = new ArrayList<>();
            List<Angajat> angajatList = (List<Angajat>)angajatiDBRepository.findAll();
            for (Angajat a : angajatList)
            {
                AngajatDTO angajatDTO = new AngajatDTO(a.getName(), a.getUsername());
                rez.add(angajatDTO);
            }
            return rez;
        }*/

        /*public List<String> getListaNumeAngajati()
        {
            List<String> rez = new ArrayList<>();
            for (Angajat a : angajatiDBRepository.findAll())
            {
                rez.add(a.getName());
            }
            return rez;
        }*/

        public int getCazId(CazDTO caz)
        {
            return cazuriDBRepository.findCazuriDupaDescriere(caz.Descriere)[0].Id;
        }

        private float getSumaAdunata(CazCaritabil c)
        {
            float rez = 0F;
            foreach (Donatie d in donatiiDBRepository.findAll())
            {
                if (d.idCazCaritabil.ToString().Equals(c.Id.ToString()))
                {
                    rez += d.sumaDonata;
                }
            }
            return rez;
        }

        public List<CazDTO> getAllCazuriDTO()
        {
            List<CazDTO> rez = new List<CazDTO>();
            List<CazCaritabil> cazCaritabilDTOS = (List<CazCaritabil>)cazuriDBRepository.findAll();
            foreach (CazCaritabil c in cazCaritabilDTOS)
            {
                CazDTO cazCaritabilDTO = new CazDTO(c.description, getSumaAdunata(c).ToString());
                rez.Add(cazCaritabilDTO);
            }
            return rez;
        }

        public List<string> getListaNumeDonatori()
        {
            List<string> rez = new List<string>();

            foreach (Donator d in donatoriDBRepository.findAll())
            {
                rez.Add(d.Name);
            }
            return rez;
        }

        public List<string> getRanduriDonatori()
        {
            List<string> rez = new List<string>();
            foreach (Donator d in donatoriDBRepository.findAll())
            {
                rez.Add(d.Name + "  " + d.Address + "  " + d.PhoneNumber);
            }

            return rez;
        }

        public void login(Angajat angajat, ITeledonObserver client)
        {
            if (angajat != null)
            {
                if (loggedClients.ContainsKey(angajat.username))
                    throw new TeledonException("User already logged!");
                loggedClients.Add(angajat.username, client);
            }
            else throw new TeledonException("Authentication failed.");
        }

        public void donatieS(float sumaDonata, int idDonator, int cazId)
        {
            Donatie d = new Donatie(sumaDonata, idDonator, cazId);
            try
            {
                validatorDonatii.valideaza(d);
            }
            catch (Exception e)
            {

            }
            donatiiDBRepository.save(d);
            notifyAllAngajatiDonatie();
        }

        private void notifyAllAngajatiDonatie()
        {

            foreach (var cl in loggedClients.Keys)
            {

                ITeledonObserver Client = loggedClients[cl];
                Task.Run(() => Client.donationMade(getAllCazuriDTO()));

            }
        }

        public void updateDonator(Donator donator)
        {
            donatoriDBRepository.update(donator);
        }

        public void saveDonator(Donator donator)
        {
            donatoriDBRepository.save(donator);
        }

        public void commitDonatori(string nume, string nrTel, string adresa)
        {
            Donator donator = null;
            try
            {
                donator = getDonator(nume);
                if (donator != null && donator.PhoneNumber.Equals(nrTel))
                {
                    Donator forUpdate = new Donator(nume, nrTel, adresa);
                    forUpdate.Id = donator.Id;
                    updateDonator(forUpdate);

                }
                else
                {
                    saveDonator(new Donator(nume, nrTel, adresa));
                }
            }
            catch (TeledonException e)
            {

            }
            notifyAllAngajatiCommit();
        }

        private void notifyAllAngajatiCommit()
        {
            foreach (var cl in loggedClients.Keys)
            {

                ITeledonObserver Client = loggedClients[cl];
                Task.Run(() => Client.commitDonatori(getDonators()));

            }
        }

        public void check(Angajat angajat)
        {
            List<Angajat> angajatList = (List<Angajat>)angajatiDBRepository.findAll();
            bool gasit = false;
            foreach (Angajat a in angajatList)
            {
                if (a.username.Equals(angajat.username) && a.password.Equals(angajat.password))
                {
                    gasit = true;
                    break;
                }
            }
            if (!gasit)
                throw new TeledonException("Datele introduse nu sunt inregistrate!");
        }

        public Donator getDonator(string nume)
        {
            var list = donatoriDBRepository.findDonatoriDupaNume(nume);
            if (list.Count == 0)
                return null;
            return list[0];
        }

        public void logout(Angajat angajat, ITeledonObserver client)
        {
            /*System.out.println("log out in service");
            System.out.println("se delogheaza " + angajat.getUsername());
            System.out.println("logati: " + loggedClients.keySet());*/
            ITeledonObserver localClient = loggedClients[angajat.username];
            if (localClient == null)
                throw new TeledonException("User " + angajat.username + " is not logged in.");
            loggedClients.Remove(angajat.username);

        }

        public List<CazDTO> getCauriDTO()
        {
            return getAllCazuriDTO();
        }

        public List<string> getDonators()
        {
            return getRanduriDonatori();
        }
    }
}
