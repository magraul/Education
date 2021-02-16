package me.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import me.box.AlertBox;
import me.services.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLControllerLogare implements Initializable, EventHandler {
    private Service service;

    @FXML
    private Button login, recuperare;

    @FXML
    private TextField username;

    @FXML private TextArea area;

    @FXML
    private PasswordField parola;

    @Override
    public void handle(Event event) {
        if (event.getSource() == login) {
            if (username.getText().equals("admin") && service.check(username.getText(), parola.getText())) {
                Stage windowLogare = (Stage) ((javafx.scene.Node) login).getScene().getWindow();
                windowLogare.hide();

                FXMLLoader loaderAdmin = new FXMLLoader();
                loaderAdmin.setLocation(getClass().getResource("/fxml/adminView.fxml"));
                Parent root = null;
                try {
                    root = loaderAdmin.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FXMLControllerAdmin ctrl =
                        loaderAdmin.<FXMLControllerAdmin>getController();
                ctrl.setService(service);

                Scene scene = new Scene(root);
                Stage window = new Stage();
                window.setTitle("Administrator");
                window.setScene(scene);
                window.show();
                return;
            }

            if (username.getText().equals("sef") && service.check(username.getText(), parola.getText())) {
                Stage windowLogare = (Stage) ((javafx.scene.Node) login).getScene().getWindow();
                windowLogare.hide();

                FXMLLoader loaderSef = new FXMLLoader();
                loaderSef.setLocation(getClass().getResource("/fxml/sefView.fxml"));
                Parent root = null;
                try {
                    root = loaderSef.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FXMLControllerSef ctrl =
                        loaderSef.<FXMLControllerSef>getController();
                ctrl.setService(service);
                ctrl.setPersoana(username.getText());

                Scene scene = new Scene(root);
                Stage window = new Stage();
                window.setTitle("Sef");
                window.setScene(scene);
                window.show();

                return;
            }

            //logare ca si angajat
            if (service.checkAngajat(username.getText(), parola.getText())) {
                Stage windowLogare = (Stage) ((javafx.scene.Node) login).getScene().getWindow();
                windowLogare.hide();

                FXMLLoader loaderOra = new FXMLLoader();
                loaderOra.setLocation(getClass().getResource("/fxml/popUpOraSosire.fxml"));
                Parent root = null;
                try {
                    root = loaderOra.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FXMLControllerOra ctrl =
                        loaderOra.<FXMLControllerOra>getController();
                ctrl.setAngajat(service.getAngajat(username.getText()));

                ctrl.setService(service);


                Scene scene = new Scene(root);
                Stage window = new Stage();
                window.setTitle(username.getText());
                window.setScene(scene);
                window.show();





            } else {
                AlertBox.display("eroare", "Date incorecte");
            }


        }

        if (event.getSource() == recuperare) {
            if (username.getText().equals("")) {
                AlertBox.display("Eroare", "Introduceti username ul");
                return;
            }
            area.setText(service.cerereDate(username.getText()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setOnAction(this);
        recuperare.setOnAction(this);
    }

    public void setService(Service service) {
        this.service = service;
    }


}
