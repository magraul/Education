using System;
using System.Collections.Generic;
using System.Text;
using teledon;
using Domain_Repo.repositories;
using DomainRepoLibrary.validators;
using servicess;
using Domain_Repo.entities;
using Thrift.Transport;
using Thrift.Protocol;
using Server;

namespace ServerS
{
    class RequestHandler : TeledonService.Iface
    {
        Service service = new Service(new AngajatiDBRepository(), new CazuriDBRepository(), new DonatiiDBRepository(), new DonatoriDBRepository(), new ValidatorCazuri(), new ValidatorDonatii(), new ValidatorDonator());

        public bool check(string userName, string password, int port)
        {
            bool reusit = false;

            foreach (Angajat angajat in service.GetAllAngajati())
            {
                if (angajat.username.Equals(userName) && angajat.password.Equals(password))
                {
                    reusit = true;
                    TTransport transport = new TSocket("localhost", port);
                    transport.Open();
                    TProtocol protocol = new TBinaryProtocol(transport);
                    UpdateClient.UpdateService.Client client = new UpdateClient.UpdateService.Client(protocol);
                    service.addClient(client);
                    Console.WriteLine("Received update client on port " + port);
                    break;
                }
            }


            return reusit;
        }

        public void donatieS(CazDTO caz, string numeDonator, string adresa, string nrTel, double sumaDonata)
        {
            DomainRepoLibrary.entities.CazDTO cazz = new DomainRepoLibrary.entities.CazDTO(caz.Descriere, (float)caz.Suma);
            service.donatieS(cazz, numeDonator, adresa, nrTel, (float)sumaDonata);
        }

        public List<CazDTO> getCauriDTO()
        {
            var cazuri = service.getCauriDTO();
            List<CazDTO> rez = new List<CazDTO>();
            cazuri.ForEach(x => {
                var cc = new teledon.CazDTO();
                cc.Descriere = x.Descriere;
                cc.Suma = x.SumaAdunata;
                rez.Add(cc);
            });

            return rez;
        }

        public List<string> getDonators()
        {
            return service.getDonators();
        }
    }
}
