using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using servicess;

namespace ProjectGUI
{
    using TeledonServerProxy = networking.TeledonServerProxy;
   
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        
        

        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            ITeledonServices server = new TeledonServerProxy("127.0.0.1", 55555);
            TeledonClientController ctrl = new TeledonClientController(server);
            LogInView logIn = new LogInView(ctrl);
            Application.Run(logIn);
        }
    }
}
