using Domain_Repo.entities;
using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using log4net.Config;
using log4net;

namespace Domain_Repo.repositories
{
    public class DonatoriDBRepository : IDonatoriRepository
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(DonatoriDBRepository));

        public DonatoriDBRepository()
        {
            log.Info("Creating DonatoriDBRepository");
        }



        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Donator> findAll()
        {
            log.Info("getting all donators");
            lock (this)
            {

                IDbConnection con = dbUtils.DBUtils.getConnection();
         /*       Npgsql.NpgsqlConnection con1 = (Npgsql.NpgsqlConnection)con;
                Npgsql.NpgsqlConnection.ClearPool(con1);*/

                List<Donator> donatori = new List<Donator>();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donatori";


                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            Donator d = new Donator(dataR.GetString(1), dataR.GetString(2), dataR.GetString(3));
                            d.Id = dataR.GetInt32(0);
                            donatori.Add(d);
                        }
                    }
                }

                log.InfoFormat("exit findall with value {0}", donatori);
                return donatori;
            }
        }

        public IList<Donator> findDonatoriDupaAdresa(string adresa)
        {
            throw new NotImplementedException();
        }

        public IList<Donator> findDonatoriDupaNume(string nume, string nrTel)
        {
            log.InfoFormat("getting donators with name {0}", nume);
            IDbConnection con = dbUtils.DBUtils.getConnection();
            List<Donator> donatori = new List<Donator>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from donatori where name=@name and phone_number=@phone";
                IDbDataParameter paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = nume;
                comm.Parameters.Add(paramName);

                IDbDataParameter paramPhone = comm.CreateParameter();
                paramPhone.ParameterName = "@phone";
                paramPhone.Value = nrTel;
                comm.Parameters.Add(paramPhone);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Donator d = new Donator(dataR.GetString(1), dataR.GetString(2), dataR.GetString(3));
                        d.Id = dataR.GetInt32(0);
                        donatori.Add(d);
                    }
                }
            }

            log.InfoFormat("exiting with value {0}", donatori);
            return donatori;
        }

        public Donator findOne(int id)
        {
            log.Info("find one donator");
            IDbConnection con = dbUtils.DBUtils.getConnection();

            using(var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Donatori where id_donator=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                using(var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int i = dataR.GetInt32(0);
                        string n = dataR.GetString(1);
                        string a = dataR.GetString(2);
                        string t = dataR.GetString(3);

                        Donator d = new Donator(n, t, a);
                        d.Id = i;
                        return d;
                    }
                }
            }
            return null;
        }

        public void save(Donator entity)
        {
            log.Info("dave donator");
            IDbConnection con = dbUtils.DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                // verificam daca exista ce vrem sa updatam
                comm.CommandText = "select * from angajati where id_angajat = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                        return;
                }



                comm.CommandText = "insert into donatori(name, phone_number, address) values (@name, @tel, @addr)";
                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = entity.Name;
                comm.Parameters.Add(paramName);

                var paramTel = comm.CreateParameter();
                paramTel.ParameterName = "@tel";
                paramTel.Value = entity.PhoneNumber;
                comm.Parameters.Add(paramTel);

                var paramAddr = comm.CreateParameter();
                paramAddr.ParameterName = "@addr";
                paramAddr.Value = entity.Address;
                comm.Parameters.Add(paramAddr);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No donator added!");
             }   
        
         }   

        public void update(Donator entity)
        {
            log.Info("update donator");
            IDbConnection con = dbUtils.DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                // verificam daca exista ce vrem sa updatam
                comm.CommandText = "select * from donatori where id_donator = @id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (!dataR.Read())
                        return;
                }



                comm.CommandText = "update donatori set name=@name, phone_number=@tel,address=@addr where id_donator = @id ";
                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = entity.Name;
                comm.Parameters.Add(paramName);

                var paramTel = comm.CreateParameter();
                paramTel.ParameterName = "@tel";
                paramTel.Value = entity.PhoneNumber;
                comm.Parameters.Add(paramTel);

                var paramAddr = comm.CreateParameter();
                paramAddr.ParameterName = "@addr";
                paramAddr.Value = entity.Address;
                comm.Parameters.Add(paramAddr);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("No donator updated!");
            }
        
    }
    }
}
