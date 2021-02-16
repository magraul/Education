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
using UpdateClient;

namespace Server
{
    public class Service
    {
        private List<UpdateService.Client> loggedClients;

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
            loggedClients = new List<UpdateService.Client>();
        }

        internal void addClient(UpdateService.Client client)
        {
            Console.WriteLine("logare client");
            this.loggedClients.Add(client);
            Console.WriteLine("clienti logati:");
            Console.WriteLine(this.loggedClients);
        }

        public int getCazId(CazDTO caz)
        {
            return cazuriDBRepository.findCazuriDupaDescriere(caz.Descriere)[0].Id;
        }

        private float getSumaAdunata(CazCaritabil c)
        {
            lock (this)
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
        }

        public List<CazDTO> getAllCazuriDTO()
        {
            lock (this)
            {
                List<CazDTO> rez = new List<CazDTO>();
                List<CazCaritabil> cazCaritabilDTOS = (List<CazCaritabil>)cazuriDBRepository.findAll();
                foreach (CazCaritabil c in cazCaritabilDTOS)
                {
                    CazDTO cazCaritabilDTO = new CazDTO(c.description, getSumaAdunata(c));
                    rez.Add(cazCaritabilDTO);
                }
                return rez;
            }
        }

        public IEnumerable<Angajat> GetAllAngajati()
        {
            lock (this)
            {
                return angajatiDBRepository.findAll();
            }
        }

        public List<string> getListaNumeDonatori()
        {
            lock (this)
            {
                List<string> rez = new List<string>();

                foreach (Donator d in donatoriDBRepository.findAll())
                {
                    rez.Add(d.Name);
                }
                return rez;
            }
        }

        public List<string> getRanduriDonatori()
        {
            lock (this)
            {
                List<string> rez = new List<string>();
                var t = donatoriDBRepository.findAll();
                foreach (Donator d in donatoriDBRepository.findAll())
                {
                    rez.Add(d.Name + "  " + d.Address + "  " + d.PhoneNumber);
                }

                return rez;
            }
        }

     /*   public void login(Angajat angajat, ITeledonObserver client)
        {
            if (angajat != null)
            {
                if (loggedClients.ContainsKey(angajat.username))
                    throw new TeledonException("User already logged!");
                loggedClients.Add(angajat.username, client);
            }
            else throw new TeledonException("Authentication failed.");
        }*/

        public void donatieS(CazDTO caz, string numeDonator, string adresa, string nrTel, float sumaDonata)
        {
            lock (this)
            {
                handleDonatori(numeDonator, nrTel, adresa);
                Int32 id = getDonator(numeDonator, nrTel).Id;
                Int32 cazId = getCazId(caz);
                Donatie d = new Donatie(sumaDonata, id, cazId);
                
                try
                {
                    validatorDonatii.valideaza(d);
                }
                catch (Exception e)
                {

                }
                
                donatiiDBRepository.save(d);
                float s = caz.SumaAdunata;
                s += sumaDonata;
                caz.SumaAdunata = s;
                notifyAllAngajatiDonatie();
            }
        }

        private void handleDonatori(string numeDonator, string nrTel, string adresa)
        {
            lock (this)
            {
                Donator donator = null;
                try
                {
                    donator = getDonator(numeDonator, nrTel);
                    if (donator != null)
                    {
                        if (!adresa.Equals(donator.Address))
                        {
                            Donator forUpdate = new Donator(numeDonator, nrTel, adresa);
                            forUpdate.Id = donator.Id;
                            updateDonator(forUpdate);
                            notifyAllDonatorUpdate(forUpdate);
                        }

                    }
                    else
                    {
                        saveDonator(new Donator(numeDonator, nrTel, adresa));
                        Donator d = getDonator(numeDonator, nrTel);
                        notifyAllDonatorAdd(d);
                    }
                }
                catch (TeledonException e)
                {
                    Console.WriteLine("exceptie donator get server");

                }
            }
        }
        private void notifyAllDonatorAdd(Donator d)
        {
            lock (this)
            {
                foreach (var cl in loggedClients)
                {


                    Task.Run(() => cl.update());//.donatorAdd(d.Name + "  " + d.Address + "  " + d.PhoneNumber));

                }
            }
        }

        private void notifyAllDonatorUpdate(Donator forUpdate)
        {
            lock (this)
            {
                foreach (var cl in loggedClients)
                {


                    Task.Run(() => cl.update());//.donatorAdd(d.Name + "  " + d.Address + "  " + d.PhoneNumber));

                }
            }
        }

        private void notifyAllAngajatiDonatie()
        {
            lock (this)
            {
                foreach (var cl in loggedClients)
                {


                    Task.Run(() => cl.update());//.donatorAdd(d.Name + "  " + d.Address + "  " + d.PhoneNumber));

                }
            }
        }

        public void updateDonator(Donator donator)
        {
            lock (this) {
                donatoriDBRepository.update(donator);
            }
        }

        public void saveDonator(Donator donator)
        {
            donatoriDBRepository.save(donator);
        }



        public void check(Angajat angajat)
        {
            lock (this)
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
        }

        public Donator getDonator(string nume, string nrTel)
        {
            lock (this)
            {
                var list = donatoriDBRepository.findDonatoriDupaNume(nume, nrTel);
                if (list.Count == 0)
                    return null;
                return list[0];
            }
        }

        public void logout(Angajat angajat, ITeledonObserver client)
        {

            /*ITeledonObserver localClient = loggedClients[angajat.username];
            if (localClient == null)
                throw new TeledonException("User " + angajat.username + " is not logged in.");
            loggedClients.Remove(angajat.username);*/

        }

        public List<CazDTO> getCauriDTO()
        {
            lock (this)
            {
                return getAllCazuriDTO();
            }
        }

        public List<string> getDonators()
        {
            lock (this)
            {
                return getRanduriDonatori();
            }
        }
    }
}
