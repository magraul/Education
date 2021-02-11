using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain_Repo.entities;
using DomainRepoLibrary.entities;

namespace servicess
{
    public interface ITeledonServices
    {
        void login(Angajat angajat, ITeledonObserver client);
        void donatieS(float sumaDonata, Int32 idDonator, Int32 cazId);
        void updateDonator(Donator donator);
        void saveDonator(Donator donator);
        void commitDonatori(string nume, string nrTel, string adresa);
        void check(Angajat angajat);
        Donator getDonator(string nume);
        void logout(Angajat angajat, ITeledonObserver client);
        List<CazDTO> getCauriDTO();
        List<string> getDonators();
        
        Int32 getCazId(CazDTO cazCaritabilDTO);
    }
}
