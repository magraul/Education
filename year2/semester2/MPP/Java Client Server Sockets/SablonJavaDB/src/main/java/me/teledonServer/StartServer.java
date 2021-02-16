package me.teledonServer;

import me.repositories.AngajatiDBRepository;
import me.repositories.CazuriDBRepository;
import me.repositories.DonatiiDBRepository;
import me.repositories.DonatoriDBRepository;
import me.teledonServer.utils.AbstractServer;
import me.teledonServer.utils.ServerException;
import me.teledonServer.utils.TeledonObjectConcurrentServer;
import me.teledonServices.ITeledonServices;
import me.validators.ValidatorCazuri;
import me.validators.ValidatorDonatii;
import me.validators.ValidatorDonatori;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverPropsPort=new Properties();
        try {
            serverPropsPort.load(new FileReader("E:\\An2 sem 2\\MPP\\networking\\ProiectMPP_Repo_Model\\SablonDBConfigLogging\\SablonJavaDB\\src\\main\\java\\me\\teledonServer\\teledonServer.config"));
            System.out.println("Server properties set. ");
            serverPropsPort.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }


            Properties serverProps = new Properties();
            try {
                serverProps.load(new FileReader("bd.config"));
                serverProps.list(System.out);
            } catch (IOException e) {
                e.printStackTrace();
            }


        ITeledonServices service = new Service(new AngajatiDBRepository(serverProps), new CazuriDBRepository(serverProps), new DonatiiDBRepository(serverProps), new DonatoriDBRepository(serverProps), new ValidatorCazuri(), new ValidatorDonatii(), new ValidatorDonatori());

        int TeledonServerPort=defaultPort;
        try {
            TeledonServerPort = Integer.parseInt(serverPropsPort.getProperty("teledon.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }

        System.out.println("Starting server on port: "+ TeledonServerPort);

        AbstractServer server = new TeledonObjectConcurrentServer(TeledonServerPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
