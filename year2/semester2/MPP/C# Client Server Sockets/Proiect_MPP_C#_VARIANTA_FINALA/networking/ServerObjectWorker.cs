using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DomainRepoLibrary.entities;
using servicess;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;

namespace networking
{
    using Angajat = Domain_Repo.entities.Angajat;
    using Donator = Domain_Repo.entities.Donator;

    public class ServerObjectWorker : ITeledonObserver
    {
        private ITeledonServices server;
        private TcpClient connection;
        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ServerObjectWorker(ITeledonServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }catch(Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }


        private Response handleRequest(Request request)
        {
            Response response = null;
            if (request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest)request;

                Angajat user = logReq.getUser();

                try
                {
                    lock (server)
                    {
                        server.login(user, this);
                    }
                    return new OkResponse();
                }
                catch (TeledonException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest)request;
                Angajat udto = logReq.getUser();

                
                try
                {
                    lock (server)
                    {

                        server.logout(udto, this);
                    }
                    connected = false;
                    return new OkResponse();

                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is DoDonationRequest){
               
                DoDonationRequest senReq = (DoDonationRequest)request;
                Int32 cazId = senReq.getCazId();
                //            String numeDonator = senReq.getNumeDonator();
                //            String adresa = senReq.getAdresa();
                //            String nrTel = senReq.getNrTel();
                float suma = senReq.getSumaDonata();
                Int32 idDonator = senReq.getIdDonator();
                try
                {
                    //server.donatieS(cazId,numeDonator,adresa,nrTel,suma);
                    server.donatieS(suma, idDonator, cazId);
                    return new OkResponse();
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetCazuriDTORequest){
                
                GetCazuriDTORequest getReq = (GetCazuriDTORequest)request;

               
                try
                {
                    List<CazDTO> cauriDTO = server.getCauriDTO();
                    return new GetCazuriDTOResponse(cauriDTO);
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetDonatoriRequest){
                Console.WriteLine("intrat in obj worker getdonator reqest");
             
                GetDonatoriRequest getReq = (GetDonatoriRequest)request;
                try
                {
                    List<string> cauriDTO = server.getDonators();
                    Console.WriteLine("se trimite" + cauriDTO);
                    return new GetDonatoriResponse(cauriDTO);
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is GetCazIdRequest){
       
                GetCazIdRequest getreq = (GetCazIdRequest)request;
                try
                {
                    Int32 id = server.getCazId(getreq.getCazCaritabilDTO());
                    return new GetCazIdResponse(id);
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetDonatorRequest){
                GetDonatorRequest getReq = (GetDonatorRequest)request;
                try
                {
                    string nume = getReq.getNume();
                    Donator donator = server.getDonator(nume);
                    return new GetDonatorResponse(donator);
                }
                catch (TeledonException exeption)
                {
                    return new ErrorResponse(exeption.Message);
                }
            }

            if (request is UpdateDonatorRequest){
               
                UpdateDonatorRequest req = (UpdateDonatorRequest)request;
                try
                {
                    server.updateDonator(req.getDonator());
                    return new OkResponse();
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is SaveDonatorRequest){
               
                SaveDonatorRequest req = (SaveDonatorRequest)request;
                try
                {
                    server.saveDonator(req.getDonator());
                    return new OkResponse();
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is commitDonatoriRequest){
     
                commitDonatoriRequest req = (commitDonatoriRequest)request;
                try
                {
                    server.commitDonatori(req.getNume(), req.getNrTel(), req.getAdresa());
                    return new OkResponse();
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is CheckRequest) {
              
                CheckRequest req = (CheckRequest)request;
                try
                {
                    server.check(req.getAngajat());
                    return new OkResponse();
                }
                catch (TeledonException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            return response;
        }



        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if(response != null)
                    {
                        sendResponse((Response)response);
                    }
                } catch(Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }catch(Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }

            try
            {
                stream.Close();
                connection.Close();
            }catch(Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }


        public virtual void commitDonatori(List<string> donatori)
        {
            try
            {
                sendResponse(new commitDonatoriResponse(donatori));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void donationMade(List<CazDTO> cazuri)
        {
            try
            {
                sendResponse(new DonationMadeResponse(cazuri));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();

        }
    }
}

