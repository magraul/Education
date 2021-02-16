using System;
using System.Collections.Generic;
using System.Text;
using System.Data;



namespace Domain_Repo.dbUtils
{
    public static class DBUtils
    {

        private static IDbConnection instance = null;

        public static IDbConnection getConnection()
        {
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = getNewConnection();
                instance.Open();
            }
            return instance;
        }
        
        private static IDbConnection getNewConnection()
        {      
            return ConnectionFactory.getInstance().createConnection();
        }

        /*public static IDbConnection getConnection()
        {
            instance = getNewConnection();
            instance.Open();
            
            return instance;
        }*/
    }
}
