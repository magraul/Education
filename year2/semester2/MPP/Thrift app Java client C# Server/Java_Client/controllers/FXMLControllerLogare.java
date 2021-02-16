package me.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.boxes.AlertBox;
import me.client.UpdateServer;
import me.entities.Angajat;
import me.observer.Observer;
import me.teledon.TeledonService;
import me.teledon.UpdateService;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class FXMLControllerLogare implements Initializable, EventHandler {
    private Properties serverProps = null;

    private TeledonService.Client server;
    private FXMLControllerAngajat angajatCtrl;

    public UpdateServer updateServer;

    private int updatePort;

    private Angajat crtUser;
    Parent mainTeledonParent;



    @FXML
    Button logIn;

    @FXML
    TextField username, parola;


    @Override
    public void handle(Event event) {
        if (event.getSource() == logIn) {
            if (username.getText().length() == 0 || parola.getText().length() == 0) {
                AlertBox.display("eroare", "toate fieldurile sunt obligatorii");
                return;
            }
            if (username.getText().equals("admin")) {
                //view admin
                try {
                    FXMLLoader loaderAdmin = new FXMLLoader();
                    loaderAdmin.setLocation(getClass().getResource("/fxml/adminView.fxml"));

                    Parent viewLogare = loaderAdmin.load();

                    Stage windowNote = (Stage) ((Node) logIn).getScene().getWindow();
                    windowNote.hide();

                    Scene scene = new Scene(viewLogare);
                    Stage window = new Stage();
                    window.setTitle("Administrator");
                    window.setScene(scene);

                    FXMLControllerAdmin ctrl = loaderAdmin.getController();

                    ctrl.setComponente();
                    window.show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    AlertBox.display("eroare", e.getMessage());
                }

            } else {

                crtUser = new Angajat(username.getText(), "", "", username.getText(), parola.getText());
                angajatCtrl.setUser(crtUser);

                //socket pentru servicul care va apela metoda de refresh din interfata
                TServerTransport serverTransport = findSocket();
                if(! verificaDate(username.getText(), parola.getText())) {
                    AlertBox.display("eroare", "date de logare invalide!");
                    return;
                }

                UpdateServer updateServer = this.updateServer;

                UpdateService.Processor processor = new UpdateService.Processor<>(updateServer);
                TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

                Observer observer = new Observer(server);
                Thread thread = new Thread(observer);
                thread.start();

                //listener ul care asteapta notificari de la serverul c# pentru a actualiza fereastra
                angajatCtrl.setServer(this.server);
                updateServer.setControllerAngajat(angajatCtrl);

                Stage stage = new Stage();
                stage.setTitle("teledon window for " + crtUser.getUsername());
                stage.setScene(new Scene(mainTeledonParent));
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        angajatCtrl.logOUT();
                        System.exit(0);
                    }
                });
                stage.show();

                Stage windowLogare = (Stage) ((Node) logIn).getScene().getWindow();
                windowLogare.hide();

                angajatCtrl.setComponente();

            }
        }
    }

    private boolean verificaDate(String text, String text1) {
        try {
            return server.check(text, text1, this.updatePort);
        } catch (TException e) {
            e.printStackTrace();
        }
        return false;
    }

    private TServerTransport findSocket() {
        TServerTransport serverTransport;
        for(int i=1000;i<=55555;i++){
            try {
                serverTransport =  new TServerSocket(i);
                if(serverTransport != null){
                    this.updatePort = i;
                    return serverTransport;
                }

            } catch (Exception e) {

            }
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logIn.setOnAction(this);
    }

    public void setServer(TeledonService.Client server) {
        this.updateServer = new UpdateServer();
        this.server = server;
    }

    public void setAngajatController(FXMLControllerAngajat controllerAngajat) {
        this.angajatCtrl = controllerAngajat;
    }

    public void setParent(Parent croot) {
        this.mainTeledonParent = croot;
    }
}
