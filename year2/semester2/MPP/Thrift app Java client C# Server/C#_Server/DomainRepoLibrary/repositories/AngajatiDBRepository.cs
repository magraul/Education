using Domain_Repo.entities;
using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using log4net;
using log4net.Config;
using Domain_Repo.dbUtils;


namespace Domain_Repo.repositories
{
    public class AngajatiDBRepository : IAngajatiRepository
    {
        private static readonly ILog log = LogManager.GetLogger(typeof(AngajatiDBRepository));

        public AngajatiDBRepository()
        {
           // BasicConfigurator.Configure();
            log.Info("Creating AngajatiDBRepository");
        }

        public void delete(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Angajat> findAll()
        {
            log.Info("getting all employees");
            IDbConnection con = dbUtils.DBUtils.getConnection();
            Npgsql.NpgsqlConnection con1 = (Npgsql.NpgsqlConnection)con;
            Npgsql.NpgsqlConnection.ClearPool(con1);

            List<Angajat> angajati = new List<Angajat>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from angajati";
               
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Angajat a = new Angajat(dataR.GetString(1), dataR.GetString(2), dataR.GetString(3), dataR.GetString(4), dataR.GetString(5));
                        a.Id = dataR.GetInt32(0);
                        angajati.Add(a);
                    }
                }
            }
            log.InfoFormat("exiting findall with value {0}", angajati);
            return angajati;
        }

        public List<Angajat> findAngajatiDupaNume(string nume)
        {
            log.InfoFormat("getting employees with name {0}", nume);
            IDbConnection con = dbUtils.DBUtils.getConnection();
            List<Angajat> angajati = new List<Angajat>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from angajati where name=@name";
                IDbDataParameter paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = nume;
                comm.Parameters.Add(paramName);
                using(var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Angajat a = new Angajat(dataR.GetString(1), dataR.GetString(2), dataR.GetString(3), dataR.GetString(4), dataR.GetString(5));
                        a.Id = dataR.GetInt32(0);
                        angajati.Add(a);
                    }
                }
            }
            log.InfoFormat("exiting function with value {0}", angajati);
            return angajati;
        }

        public List<Angajat> findAngajatiDupaUsername(string nume)
        {
            log.InfoFormat("getting employees with username {0}", nume);
            IDbConnection con = dbUtils.DBUtils.getConnection();
            List<Angajat> angajati = new List<Angajat>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from angajati where username=@name";
                IDbDataParameter paramName = comm.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = nume;
                comm.Parameters.Add(paramName);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Angajat a = new Angajat(dataR.GetString(1), dataR.GetString(2), dataR.GetString(3), dataR.GetString(4), dataR.GetString(5));
                        a.Id = dataR.GetInt32(0);
                        angajati.Add(a);
                    }
                }
            }
            log.InfoFormat("exiting function with value {0}", angajati);
            return angajati;
        }

        public Angajat findOne(int id)
        {
            throw new NotImplementedException();
        }

        public void save(Angajat entity)
        {
            throw new NotImplementedException();
        }

        public void update(Angajat entity)
        {
            log.InfoFormat("updating employeee with id {0}", entity.Id);
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
                    if (!dataR.Read())
                    {
                        log.Info("exiting update without update");
                        return;
                    }
                }



                comm.CommandText = "update angajati set name=@name, phone_number=@tel,address=@addr,username=@user,password=@pass where id_angajat = @id ";
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

                IDbDataParameter paramUser = comm.CreateParameter();
                paramUser.ParameterName = "@user";
                paramUser.Value = entity.username;
                comm.Parameters.Add(paramUser);

                IDbDataParameter paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = entity.password;
                comm.Parameters.Add(paramPass);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Error("no employee updated");
                    throw new RepositoryException("No employee updated!");
                }
            }
            log.Info("exiting update function succes");
        }
    }
}
