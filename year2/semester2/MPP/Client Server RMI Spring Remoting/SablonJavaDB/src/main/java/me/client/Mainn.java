package me.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.FXMLControllerAngajat;
import me.FXMLControllerLogare;
import me.teledonServices.ITeledonServices;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mainn extends Application {
//    @Override
//    public void start(Stage window) throws Exception {
//        FXMLLoader loaderLogare = new FXMLLoader();
//        loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
//        Parent root = loaderLogare.load();
//
//        Scene scene = new Scene(root);
//
//        window.setTitle("Logare");
//        window.setScene(scene);
//        FXMLControllerLogare ctrl = loaderLogare.getController();
//        ctrl.setComponente();
//        window.show();
//    }

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage window) throws Exception {
//        System.out.println("In start");
//        Properties clientProps = new Properties();
//        try {
//            clientProps.load(new FileReader("E:\\An2 sem 2\\MPP\\networking\\ProiectMPP_Repo_Model\\SablonDBConfigLogging\\SablonJavaDB\\src\\main\\java\\me\\client\\client.config"));
//            System.out.println("Client properties set. ");
//            clientProps.list(System.out);
//        } catch (IOException e) {
//            System.err.println("Cannot find chatclient.properties " + e);
//            return;
//        }
//        String serverIP = clientProps.getProperty("teledon.server.host", defaultServer);
//        int serverPort = defaultChatPort;
//
//        try {
//            serverPort = Integer.parseInt(clientProps.getProperty("teledon.server.port"));
//        } catch (NumberFormatException ex) {
//            System.err.println("Wrong port number " + ex.getMessage());
//            System.out.println("Using default port: " + defaultChatPort);
//        }
//        System.out.println("Using server IP " + serverIP);
//        System.out.println("Using server port " + serverPort);
//
//        ITeledonServices server = new TeledonServicesObjectProxy(serverIP, serverPort);
//
//        FXMLLoader loaderLogare = new FXMLLoader();
//        loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
//        Parent root = loaderLogare.load();
//
//        FXMLControllerLogare ctrl =
//                loaderLogare.<FXMLControllerLogare>getController();
//        ctrl.setServer(server);
//
//
//
//
//        FXMLLoader loaderAngajat = new FXMLLoader();
//        loaderAngajat.setLocation(getClass().getResource("/fxml/noteView.fxml"));
//        Parent rootAngajat=loaderAngajat.load();
//
//        FXMLControllerAngajat controllerAngajat =
//                loaderAngajat.<FXMLControllerAngajat>getController();
//        controllerAngajat.setServer(server);
//
//        ctrl.setAngajatController(controllerAngajat);
//        ctrl.setParent(rootAngajat);
//
//        Scene scene = new Scene(root);
//        window.setScene(scene);
//        window.show();

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-config.xml");
        ITeledonServices server = (ITeledonServices)factory.getBean("teledonService");
        System.out.println("am obtinut ref la remote server");

        FXMLLoader loaderLogare = new FXMLLoader();
        loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
        Parent root = loaderLogare.load();

        FXMLControllerLogare ctrl =
                loaderLogare.<FXMLControllerLogare>getController();
        ctrl.setServer(server);


        FXMLLoader loaderAngajat = new FXMLLoader();
        loaderAngajat.setLocation(getClass().getResource("/fxml/noteView.fxml"));
        Parent rootAngajat=loaderAngajat.load();

        FXMLControllerAngajat controllerAngajat =
                loaderAngajat.<FXMLControllerAngajat>getController();
        controllerAngajat.setServer(server);

        ctrl.setAngajatController(controllerAngajat);
        ctrl.setParent(rootAngajat);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();


    }

}

