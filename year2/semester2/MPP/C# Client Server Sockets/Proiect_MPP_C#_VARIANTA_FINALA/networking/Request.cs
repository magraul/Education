using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain_Repo.entities;

namespace networking
{
    using CazDTO = DomainRepoLibrary.entities.CazDTO;
    using Donator = Domain_Repo.entities.Donator;

    interface Request
    {
    }

    [Serializable]
    public class LoginRequest : Request
    {
    private Angajat user;

    public LoginRequest(Angajat user)
    {
        this.user = user;
    }

    public Angajat getUser()
    {
        return user;
    }
}

[Serializable]
    public class CheckRequest : Request
    {
        private Angajat angajat;
        public CheckRequest(Angajat angajat)
        {
            this.angajat = angajat;
        }

        public Angajat getAngajat()
        {
            return angajat;
        }
    }

    [Serializable]
    public class commitDonatoriRequest : Request
    {
        private string nume, nrTel, adresa;

        public commitDonatoriRequest(string nume, string nrTel, string adresa)
        {
            this.nume = nume;
            this.nrTel = nrTel;
            this.adresa = adresa;
        }

        public string getNume()
        {
            return nume;
        }

        public string getNrTel()
        {
            return nrTel;
        }

        public string getAdresa()
        {
            return adresa;
        }
    }

    [Serializable]
    public class DoDonationRequest : Request
    {
        private int cazId, idDonator;
        private float sumaDonata;


        public DoDonationRequest(float sumaDonata, int idDonator, int cazId)
        {
            this.sumaDonata = sumaDonata;
            this.idDonator = idDonator;
            this.cazId = cazId;
        }

        public int getCazId()
        {
            return cazId;
        }

        public int getIdDonator()
        {
            return idDonator;
        }

        public float getSumaDonata()
        {
            return sumaDonata;
        }
    }

    [Serializable]
    public class GetAngajatiDTORequest : Request
    {
    }


    [Serializable]
    public class GetCazIdRequest : Request
    {
        private CazDTO cazCaritabilDTO;

        public GetCazIdRequest(CazDTO cazCaritabilDTO)
        {
            this.cazCaritabilDTO = cazCaritabilDTO;
        }

        public CazDTO getCazCaritabilDTO()
        {
            return cazCaritabilDTO;
        }
    }

    [Serializable]
    public class GetCazuriDTORequest : Request
    {
     
    }

    [Serializable]
    public class GetDonatoriRequest : Request
    {

    }

    [Serializable]
    public class GetDonatorRequest : Request
    {
    private string nume;

    public GetDonatorRequest(string nume)
    {
        this.nume = nume;
    }

    public string getNume()
    {
        return nume;
    }
}

    [Serializable]
    public class LogoutRequest : Request
    {
    private Angajat user;

    public LogoutRequest(Angajat user)
    {
        this.user = user;
    }

    public Angajat getUser()
    {
        return user;
    }
}
    [Serializable]
    public class SaveDonatorRequest : Request
    {
    private Donator donator;

    public SaveDonatorRequest(Donator donator)
    {
        this.donator = donator;
    }

    public Donator getDonator()
    {
        return donator;
    }
}

    [Serializable]
    public class UpdateDonatorRequest : Request
    {
    private Donator donator;

    public UpdateDonatorRequest(Donator donator)
    {
        this.donator = donator;
    }

    public Donator getDonator()
    {
        return donator;
    }
}




}
