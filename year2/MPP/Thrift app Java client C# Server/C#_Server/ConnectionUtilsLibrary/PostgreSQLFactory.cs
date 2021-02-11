using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using Npgsql;

namespace Domain_Repo.dbUtils
{
    public class PostgreSQLFactory : ConnectionFactory
    {
		public override IDbConnection createConnection()
		{
			//MySql Connection
			string connectionString = string.Format("Server={0};Port={1};" +
		 "User Id={2};Password={3};Database={4};",
		 "localhost", "1234", "postgres", "magraul", "entitati");

			
			return new NpgsqlConnection(connectionString);


		}
	}
}
