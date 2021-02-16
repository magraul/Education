using Domain_Repo.entities;
using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using log4net;
using log4net.Config;

namespace Domain_Repo.repositories
{
    public class DonatiiDBRepository : IDonatiiRepository
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(DonatiiDBRepository));

        public DonatiiDBRepository()
        {
            log.Info("Creating DonatiiDBRepository");
        }
        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Donatie> findAll()
        {
            lock (this)
            {
                log.Info("getting all donations");
                IDbConnection con = dbUtils.DBUtils.getConnection();
           /*     Npgsql.NpgsqlConnection con1 = (Npgsql.NpgsqlConnection)con;
                Npgsql.NpgsqlConnection.ClearPool(con1);*/

                List<Donatie> donatii = new List<Donatie>();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donatii";

                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            Donatie d = new Donatie((float)dataR.GetDouble(3), dataR.GetInt32(2), dataR.GetInt32(1));
                            d.Id = dataR.GetInt32(0);
                            donatii.Add(d);
                        }
                    }
                }
                log.InfoFormat("exiting with value {0}", donatii);
                return donatii;
            }
        }

        public List<Donatie> findDonatiiDupaCaz(CazCaritabil cazCaritabil)
        {
            throw new NotImplementedException();
        }

        public List<Donatie> findDonatiiDupaDonator(Donator donator)
        {
            throw new NotImplementedException();
        }

        public List<Donatie> findDonatiiDupaSuma(float suma)
        {
            throw new NotImplementedException();
        }

        public Donatie findOne(int id)
        {
            throw new NotImplementedException();
        }

        public void save(Donatie entity)
        {
            log.InfoFormat("adding donation for {0}", entity.idCazCaritabil);
            IDbConnection con = dbUtils.DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                // verificam daca exista ce vrem sa updatam
                comm.CommandText = "select * from donatii where id_donatie = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                        return;
                }



                comm.CommandText = "insert into donatii(id_caz, id_donator, suma) values (@caz, @don, @suma)";
                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@caz";
                paramName.Value = entity.idCazCaritabil;
                comm.Parameters.Add(paramName);

                var paramTel = comm.CreateParameter();
                paramTel.ParameterName = "@don";
                paramTel.Value = entity.idDonator;
                comm.Parameters.Add(paramTel);

                var paramAddr = comm.CreateParameter();
                paramAddr.ParameterName = "@suma";
                paramAddr.Value = entity.sumaDonata;
                comm.Parameters.Add(paramAddr);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Error("No donation added!");
                    throw new RepositoryException("No donation added!");
                }
            }
            log.Info("exit save");
        }

        public void update(Donatie entity)
        {
            throw new NotImplementedException();
        }
    }
}
