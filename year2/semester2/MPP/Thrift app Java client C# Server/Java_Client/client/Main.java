package me.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.controllers.FXMLControllerAngajat;
import me.controllers.FXMLControllerLogare;
import me.teledon.TeledonService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage window) throws Exception {
        System.out.println(System.getProperty("java.version"));
        System.out.println("In start");
        try {

        FXMLLoader loaderLogare = new FXMLLoader();
        loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
        Parent root = loaderLogare.load();



            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            TeledonService.Client client = new TeledonService.Client(protocol);




        FXMLControllerLogare ctrl =
                loaderLogare.<FXMLControllerLogare>getController();
        ctrl.setServer(client);

        FXMLLoader loaderAngajat = new FXMLLoader();
        loaderAngajat.setLocation(getClass().getResource("/fxml/noteView.fxml"));
        Parent rootAngajat=loaderAngajat.load();

        FXMLControllerAngajat controllerAngajat =
                loaderAngajat.<FXMLControllerAngajat>getController();
        controllerAngajat.setServer(client);

        ctrl.setAngajatController(controllerAngajat);
        ctrl.setParent(rootAngajat);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
