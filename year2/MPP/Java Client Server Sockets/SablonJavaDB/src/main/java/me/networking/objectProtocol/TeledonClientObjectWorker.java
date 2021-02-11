package me.networking.objectProtocol;

import me.entities.Angajat;
import me.entities.Donatie;
import me.entities.Donator;
import me.networking.dto.AngajatDTO;
import me.networking.dto.CazCaritabilDTO;
import me.networking.dto.DonatieDTO;
import me.teledonServices.ITeledonObserver;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;
import org.apache.logging.log4j.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TeledonClientObjectWorker implements Runnable, ITeledonObserver {
    private ITeledonServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public TeledonClientObjectWorker(ITeledonServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                System.out.println(request);
                Object response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }




    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            Angajat angajat=logReq.getUser();
            try {
                server.login(angajat, this);
                return new OkResponse();
            } catch (TeledonExeption e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq=(LogoutRequest)request;
            Angajat udto=logReq.getUser();

            try {
                System.out.println("se delogheaza " + udto.getUsername() + " " +udto.getPassword());
                server.logout(udto, this);
                connected=false;
                System.out.println("a reusit log out in worker");
                return new OkResponse();

            } catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof DoDonationRequest){
            System.out.println("SendDonationRequest ...");
            DoDonationRequest senReq=(DoDonationRequest) request;
             Integer cazId = senReq.getCazId();
//            String numeDonator = senReq.getNumeDonator();
//            String adresa = senReq.getAdresa();
//            String nrTel = senReq.getNrTel();
            Float suma = senReq.getSumaDonata();
            Integer idDonator = senReq.getIdDonator();
            try {
                //server.donatieS(cazId,numeDonator,adresa,nrTel,suma);
                server.donatieS(suma, idDonator, cazId);
                return new OkResponse();
            } catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetCazuriDTORequest){
            System.out.println("GetLoggedFriends Request ...");
            GetCazuriDTORequest getReq=(GetCazuriDTORequest) request;

            Angajat angajat = getReq.getAngajat();
            try {
                List<CazCaritabilDTO> cauriDTO = server.getCauriDTO();
                return new GetCazuriDTOResponse(cauriDTO);
            } catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetDonatoriRequest){
            System.out.println("GetLoggedFriends Request ...");
            GetDonatoriRequest getReq=(GetDonatoriRequest) request;
            try {
                List<String> cauriDTO = server.getDonators();
                return new GetDonatoriResponse(cauriDTO);
            } catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetAngajatiDTORequest){
            System.out.println("get angajati");
            GetAngajatiDTORequest getA = (GetAngajatiDTORequest) request;
            try {
                List<AngajatDTO> angajatDTOS = server.getAngajati();
                return new GetAngajatiDTOResponse(angajatDTOS);
            }catch (TeledonExeption e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetListaNumeAngajatiRequest){
            System.out.println("get nume angajati");
            GetListaNumeAngajatiRequest getN = (GetListaNumeAngajatiRequest) request;
            try {
                List<String> nume = server.getListaNumeAngajati();
                return new GetListaNumeAngajatiResponse(nume);
            }catch (TeledonExeption e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof UpdateAngajatRequest){
            System.out.println("update angajat");
            UpdateAngajatRequest req = (UpdateAngajatRequest) request;
            try {
                server.updateAngajat(req.getA(), req.getP());
                return new OkResponse();
            }catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetCazIdRequest){
            System.out.println("get id caz");
            GetCazIdRequest getreq = (GetCazIdRequest) request;
            try {
                Integer id = server.getCazId(getreq.getCazCaritabilDTO());
                return new GetCazIdResponse(id);
            }catch (TeledonExeption e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetDonatorRequest){
            GetDonatorRequest getReq = (GetDonatorRequest)request;
            try {
                String nume = getReq.getNume();
                Donator donator = server.getDonator(nume);
                return new GetDonatorResponse(donator);
            }catch (TeledonExeption exeption){
                return new ErrorResponse(exeption.getMessage());
            }
        }

        if (request instanceof UpdateDonatorRequest){
            System.out.println("update donator");
            UpdateDonatorRequest req = (UpdateDonatorRequest) request;
            try {
                server.updateDonator(req.getDonator());
                return new OkResponse();
            }catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof SaveDonatorRequest){
            System.out.println("save donator");
            SaveDonatorRequest req = (SaveDonatorRequest) request;
            try {
                server.saveDonator(req.getDonator());
                return new OkResponse();
            }catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof commitDonatoriRequest){
            System.out.println("commit donatori");
            commitDonatoriRequest req = (commitDonatoriRequest) request;
            try {
                server.commitDonatori(req.getNume(), req.getNrTel(), req.getAdresa());
                return new OkResponse();
            }catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof CheckRequest) {
            System.out.println("checking...");
            CheckRequest req = (CheckRequest) request;
            try {
                server.check(req.getAngajat());
                return new OkResponse();
            }catch (TeledonExeption e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }


    @Override
    public void donationMade(List<String> donatori, List<CazCaritabilDTO> cazuri) throws TeledonExeption {
        try {
            sendResponse(new DonationMadeResponse(cazuri, donatori));
        } catch (IOException e) {
            System.out.println("sending error");
        }

    }

    @Override
    public void commitDonatori(List<String> donatori) throws TeledonExeption {
        try {
            sendResponse(new commitDonatoriResponse(donatori));
        }catch (IOException e){
            System.out.println("err");
        }
    }
}
