using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DomainRepoLibrary.entities;

namespace servicess
{
    public interface ITeledonObserver
    {
        void donationMade(List<CazDTO> cazuri);
        void commitDonatori(List<string> donatori);
    }
}
