using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    using CazDTO = DomainRepoLibrary.entities.CazDTO;
    using Donator = Domain_Repo.entities.Donator;

    public interface Response
    {
    }

    [Serializable]
    public class OkResponse : Response
    {

    }

    [Serializable]
    public class ErrorResponse : Response
    {
        public string Message { get; }

        public ErrorResponse(string message)
        {
            this.Message = message;
        }
    }

    public interface UpdateResponse : Response
    {

    }

    [Serializable]
    public class commitDonatoriResponse : UpdateResponse
    {
        List<string> lis;

        public commitDonatoriResponse(List<string> lis)
        {
            this.lis = lis;
        }

        public List<string> getLis() { return lis; }
    }

    [Serializable]
    public class DonationMadeResponse : UpdateResponse
    {
        private List<CazDTO> cazuri;

        public DonationMadeResponse(List<CazDTO> cazuri)
        {
            this.cazuri = cazuri;
        }

        public List<CazDTO> getCazuri()
        {
            return cazuri;
        }

    }

    [Serializable]
    public class GetCazIdResponse : Response
    {
        private Int32 id;

        public GetCazIdResponse(Int32 id)
        {
            this.id = id;
        }

        public Int32 getId()
        {
            return id;
        }
    }

    [Serializable]
    public class GetCazuriDTOResponse : Response
    {
        private List<CazDTO> cazuri;

        public GetCazuriDTOResponse(List<CazDTO> cazuri)
        {
            this.cazuri = cazuri;
        }

        public List<CazDTO> getCazuri()
        {
            return cazuri;
        }
    }

    [Serializable]
    public class GetDonatoriResponse : Response
    {
        private List<string> randuri;

        public GetDonatoriResponse(List<string> randuri)
        {
            this.randuri = randuri;
        }

        public List<string> getRanduri()
        {
            return randuri;
        }
    }

    [Serializable]
    public class GetDonatorResponse : Response
    {
        private Donator donator;

        public GetDonatorResponse(Donator donator)
        {
            this.donator = donator;
        }

        public Donator getDonator()
        {
            return donator;
        }
    }



}
