using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DomainRepoLibrary.entities
{
    [Serializable]
    public class CazDTO
    {
        public string Descriere { get; set; }
        public string SumaAdunata { get; set; }

        public CazDTO(string Desc, string suma)
        {
            this.Descriere = Desc;
            this.SumaAdunata = suma;
        }
    }
}
