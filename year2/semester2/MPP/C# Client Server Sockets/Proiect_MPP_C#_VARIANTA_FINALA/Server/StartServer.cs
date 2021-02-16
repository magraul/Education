using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain_Repo.repositories;
using DomainRepoLibrary.validators;
using servicess;
using networking;
using System.Threading;
using System.Net.Sockets;

namespace Server
{
    class StartServer
    {
        static void Main(string[] args)
        {
           
            ITeledonServices service = new Service(new AngajatiDBRepository(), new CazuriDBRepository(), new DonatiiDBRepository(), new DonatoriDBRepository(), new ValidatorCazuri(), new ValidatorDonatii(), new ValidatorDonator());
            TeledonServer server = new TeledonServer("127.0.0.1", 55555, service);
            server.Start();
            Console.ReadLine();

        }
    }

    public class TeledonServer : ConcurrentServer
    {
        private ITeledonServices server;
        private ServerObjectWorker worker;

        public TeledonServer(string host, int port, ITeledonServices server) : base(host, port)
        {
            this.server = server;
        }


        protected override Thread createWorker(TcpClient client)
        {
            worker = new ServerObjectWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
