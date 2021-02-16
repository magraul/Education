using Domain_Repo.entities;
using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using log4net;
using log4net.Config;

namespace Domain_Repo.repositories
{
    public class CazuriDBRepository : ICazuriRepository
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(CazuriDBRepository));

        public CazuriDBRepository()
        {
            log.Info("Creating CazuriDBRepository");
        }
        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<CazCaritabil> findAll()
        {
            log.Info("getting all cases");
            lock (this)
            {
                IDbConnection con = dbUtils.DBUtils.getConnection();
                Npgsql.NpgsqlConnection con1 = (Npgsql.NpgsqlConnection)con;
                Npgsql.NpgsqlConnection.ClearPool(con1);

                List<CazCaritabil> cazuri = new List<CazCaritabil>();
                using (var comm = con1.CreateCommand())
                {
                    comm.CommandText = "select * from cazuri";

                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            CazCaritabil c = new CazCaritabil(dataR.GetString(1));
                            c.Id = dataR.GetInt32(0);
                            cazuri.Add(c);
                        }
                    }
                }
                log.InfoFormat("exiting findall with value", cazuri);
                return cazuri;
            }
        }

        public List<CazCaritabil> findCazuriDupaDescriere(string descriere)
        {
            log.InfoFormat("getting cases with {}", descriere);
            IDbConnection con = dbUtils.DBUtils.getConnection();
            List<CazCaritabil> cazuri = new List<CazCaritabil>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from cazuri where descriere=@desc";
                IDbDataParameter paramDesc = comm.CreateParameter();
                paramDesc.ParameterName = "@desc";
                paramDesc.Value = descriere;
                comm.Parameters.Add(paramDesc);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        CazCaritabil c = new CazCaritabil(dataR.GetString(1));
                        c.Id = dataR.GetInt32(0);
                        cazuri.Add(c);
                    }
                }
            }
            log.InfoFormat("exiting function with values {0}", cazuri);
            return cazuri;
        }

        public CazCaritabil findOne(int id)
        {
            throw new NotImplementedException();
        }

        public void save(CazCaritabil entity)
        {
            throw new NotImplementedException();
        }

        public void update(CazCaritabil entity)
        {
            throw new NotImplementedException();
        }
    }
}
