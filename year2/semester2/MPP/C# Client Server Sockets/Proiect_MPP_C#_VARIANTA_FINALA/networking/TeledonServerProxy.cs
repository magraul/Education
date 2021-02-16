using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using servicess;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;

namespace networking
{
    using CazDTO = DomainRepoLibrary.entities.CazDTO;
    using Angajat = Domain_Repo.entities.Angajat;
    using Donator = Domain_Repo.entities.Donator;

    public class TeledonServerProxy : ITeledonServices
    {
        private string host;
        private int port;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;

        private ITeledonObserver client;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public TeledonServerProxy(string host, int port)
        {
            this.port = port;
            this.host = host;
            responses = new Queue<Response>();
        }

        public void check(Angajat angajat)
        {
            sendRequest(new CheckRequest(angajat));
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse errorResponse = (ErrorResponse)response;
                throw new TeledonException(errorResponse.Message);
            }
        }

        public void commitDonatori(string nume, string nrTel, string adresa)
        {
            sendRequest(new commitDonatoriRequest(nume, nrTel, adresa));
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
        }

        public void donatieS(float sumaDonata, int idDonator, int cazId)
        {
            sendRequest(new DoDonationRequest(sumaDonata, idDonator, cazId));
            Response response = readResponse();
           
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);

            }
        }

        public List<CazDTO> getCauriDTO()
        {
            sendRequest(new GetCazuriDTORequest());
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
            GetCazuriDTOResponse resp = (GetCazuriDTOResponse)response;
            List<CazDTO> frDTO = resp.getCazuri();
            return frDTO;
        }

        public int getCazId(CazDTO cazCaritabilDTO)
        {
            sendRequest(new GetCazIdRequest(cazCaritabilDTO));
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
            GetCazIdResponse response1 = (GetCazIdResponse)response;
            return response1.getId();
        }

        public Donator getDonator(string nume)
        {
            sendRequest(new GetDonatorRequest(nume));
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
            GetDonatorResponse resp = (GetDonatorResponse)response;
            Donator frDTO = resp.getDonator();
            return frDTO;
        }

        public List<string> getDonators()
        {
            sendRequest(new GetDonatoriRequest());
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
            GetDonatoriResponse resp = (GetDonatoriResponse)response;
            List<string> frDTO = resp.getRanduri();
            return frDTO;
        }

        public virtual void login(Angajat angajat, ITeledonObserver client)
        {
            initializeConnection();
            sendRequest(new LoginRequest(angajat));
            Response response = readResponse();

            if( response is OkResponse)
            {
                this.client = client;
                return;
            }

            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new TeledonException(err.Message);
            }
        }

        public void logout(global::Domain_Repo.entities.Angajat angajat, ITeledonObserver client)
        {
            sendRequest(new LogoutRequest(angajat));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
        }

        public void saveDonator(global::Domain_Repo.entities.Donator donator)
        {
            sendRequest(new SaveDonatorRequest(donator));
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
        }

        public void updateDonator(global::Domain_Repo.entities.Donator donator)
        {
            sendRequest(new UpdateDonatorRequest(donator));
            Response response = readResponse();
            if (response is ErrorResponse) {
                ErrorResponse err = (ErrorResponse)response;
                throw new TeledonException(err.Message);
            }
        }


        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {

                        lock (responses)
                        {


                            responses.Enqueue((Response)response);

                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

        private void handleUpdate(UpdateResponse update)
        {
            if (update is DonationMadeResponse) {
                DonationMadeResponse response = (DonationMadeResponse)update;
                try
                {
                   
                    client.donationMade(response.getCazuri());
                }
                catch (TeledonException e)
                {
                    Console.WriteLine(e.Message);
                }
            }

            if (update is commitDonatoriResponse) {
                commitDonatoriResponse response = (commitDonatoriResponse)update;
                try
                {
                    client.commitDonatori(response.getLis());
                }
                catch (TeledonException e)
                {

                }
            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new TeledonException("Error sending object " + e);
            }

        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();

                }


            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                //output.close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

    }
}
