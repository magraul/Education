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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TeledonServicesObjectProxy implements ITeledonServices {
    private String host;
    private int port;

    private ITeledonObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<Response> responses;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public TeledonServicesObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses = new LinkedBlockingQueue<Response>();
    }


    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws TeledonExeption {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TeledonExeption("Error sending object " + e);
        }

    }

    private Response readResponse() throws TeledonExeption {
        Response response = null;
        try {

            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws TeledonExeption {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(UpdateResponse update) {
        if (update instanceof DonationMadeResponse) {
            DonationMadeResponse response = (DonationMadeResponse) update;
            try {
                client.donationMade(response.getRanduri(), response.getCazuri());
            } catch (TeledonExeption e) {
                e.printStackTrace();
            }
        }

        if (update instanceof commitDonatoriResponse) {
            commitDonatoriResponse response = (commitDonatoriResponse) update;
            try {
                client.commitDonatori(response.getLis());
            }catch (TeledonExeption e){

            }
        }

    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
                        /*responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
//                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
//                    System.out.println("Reading error "+e);
                }
            }
        }
    }


    @Override
    public void login(Angajat angajat, ITeledonObserver client) throws TeledonExeption {
        initializeConnection();
        sendRequest(new LoginRequest(angajat));
        Response response = readResponse();
        if (response instanceof OkResponse) {
            this.client = client;
            return;
        }
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new TeledonExeption(err.getMessage());
        }
    }

//    @Override
//    public void donatieS(Integer cazId, String numeDonator, String adresa, String nrTel, Float sumaDonata) throws TeledonExeption {
//        sendRequest(new DoDonationRequest(cazId, numeDonator, adresa, nrTel, sumaDonata));
//        Response response = readResponse();
//        System.out.println("response de la donatie:");
//        System.out.println(response.toString());
//        if (response instanceof ErrorResponse) {
//            ErrorResponse err = (ErrorResponse) response;
//            throw new TeledonExeption(err.getMessage());
//
//        }
//    }

//    @Override
//    public void donatieS(Donatie donatie) throws TeledonExeption {
//        sendRequest(new DoDonationRequest(donatie));
//        Response response = readResponse();
//        System.out.println("response de la donatie:");
//        System.out.println(response.toString());
//        if (response instanceof ErrorResponse) {
//            ErrorResponse err = (ErrorResponse) response;
//            throw new TeledonExeption(err.getMessage());
//
//        }
//    }

//    @Override
//    public void donatieS(DonatieDTO donatie) throws TeledonExeption {
//        sendRequest(new DoDonationRequest(donatie));
//        Response response = readResponse();
//        System.out.println("response de la donatie:");
//        System.out.println(response.toString());
//        if (response instanceof ErrorResponse) {
//            ErrorResponse err = (ErrorResponse) response;
//            throw new TeledonExeption(err.getMessage());
//
//        }
//    }



    @Override
    public void donatieS(Float sumaDonata, Integer idDonator, Integer cazId) throws TeledonExeption {
        sendRequest(new DoDonationRequest(sumaDonata, idDonator, cazId));
        Response response = readResponse();
        System.out.println("response de la donatie:");
        System.out.println(response.toString());
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());

        }
    }

    @Override
    public void updateDonator(Donator donator) throws TeledonExeption {
        sendRequest(new UpdateDonatorRequest(donator));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
    }

    @Override
    public void saveDonator(Donator donator) throws TeledonExeption {
        sendRequest(new SaveDonatorRequest(donator));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
    }

    @Override
    public void commitDonatori(String nume, String nrTel, String adresa) throws TeledonExeption {
        sendRequest(new commitDonatoriRequest(nume, nrTel, adresa));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
    }

    @Override
    public void check(Angajat angajat) throws TeledonExeption {
        sendRequest(new CheckRequest(angajat));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new TeledonExeption(errorResponse.getMessage());
        }
    }

    @Override
    public Donator getDonator(String nume) throws TeledonExeption {
        sendRequest(new GetDonatorRequest(nume));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
        GetDonatorResponse resp = (GetDonatorResponse) response;
        Donator frDTO = resp.getDonator();
        return frDTO;
    }

    @Override
    public void logout(Angajat angajat, ITeledonObserver client) throws TeledonExeption {
        sendRequest(new LogoutRequest(angajat));
        Response response = readResponse();
        closeConnection();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
    }

    @Override
    public List<CazCaritabilDTO> getCauriDTO() throws TeledonExeption {
        sendRequest(new GetCazuriDTORequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
        GetCazuriDTOResponse resp = (GetCazuriDTOResponse) response;
        List<CazCaritabilDTO> frDTO = resp.getCazuri();
        return frDTO;
    }

    @Override
    public List<String> getDonators() throws TeledonExeption {
        sendRequest(new GetDonatoriRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
        GetDonatoriResponse resp = (GetDonatoriResponse) response;
        List<String> frDTO = resp.getRanduri();
        return frDTO;
    }

    @Override
    public List<AngajatDTO> getAngajati() throws TeledonExeption {
        sendRequest(new GetAngajatiDTORequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
        GetAngajatiDTOResponse response1 = (GetAngajatiDTOResponse) response;
        List<AngajatDTO> ang = response1.getAngajati();
        return ang;
    }

    @Override
    public List<String> getListaNumeAngajati() throws TeledonExeption {
        sendRequest(new GetListaNumeAngajatiRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }

        GetListaNumeAngajatiResponse response1 = (GetListaNumeAngajatiResponse) response;
        List<String> nume = response1.getNume();
        return nume;
    }

    @Override
    public void updateAngajat(AngajatDTO a, String parola) throws TeledonExeption {
        sendRequest(new UpdateAngajatRequest(a, parola));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
    }

    @Override
    public Integer getCazId(CazCaritabilDTO cazCaritabilDTO) throws TeledonExeption {
        sendRequest(new GetCazIdRequest(cazCaritabilDTO));
        Response response = readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new TeledonExeption(err.getMessage());
        }
        GetCazIdResponse response1 = (GetCazIdResponse) response;
        return response1.getId();
    }


}
