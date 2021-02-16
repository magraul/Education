using System;
using System.Collections.Generic;
using System.Text;

namespace Lab9
{
    class RepoJucatoriActivi : AbstractFileRepository<string, JucatorActiv>
    {
        public RepoJucatoriActivi(string fileName) : base(fileName, EntityToFileMapping.CreateJucatorActiv)
        {

        }

        public override string getLineFromEntity(JucatorActiv e)
        {
            return e.Id + "," + e.IdMeci + "," + e.NrPuncteInscrise + "," + e.tip;
        }
    }
}
