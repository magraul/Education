package me.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.entities.Angajat;
import me.services.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class FXMLControllerOra implements Initializable, EventHandler {
    private Angajat angajat;
    private Service service;

    @FXML private TextField angajatT, ora;
    @FXML private Button prezent;

    @Override
    public void handle(Event event) {
        if (event.getSource() == prezent) {
            try {
                LocalTime t = LocalTime.parse(ora.getText());
                Stage windowLogare = (Stage) ((javafx.scene.Node) prezent).getScene().getWindow();
                windowLogare.hide();

                service.logareAngajat(this.angajat, ora.getText());

                FXMLLoader loaderAngajat = new FXMLLoader();
                loaderAngajat.setLocation(getClass().getResource("/fxml/viewAngajat.fxml"));
                Parent root = null;
                try {
                    root = loaderAngajat.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FXMLControllerAngajat ctrl =
                        loaderAngajat.<FXMLControllerAngajat>getController();
                ctrl.setAngajat(this.angajat);
                ctrl.setService(service);


                Scene scene = new Scene(root);
                Stage window = new Stage();
                window.setOnCloseRequest(event1 -> service.plecareAngajat(angajat));
                window.setTitle(angajatT.getText());
                window.setScene(scene);
                window.show();

            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ora introdusa este invalida");
                alert.setContentText("Ora trebuie sa fie in formatul HH:mm");

                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prezent.setOnAction(this);
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
        angajatT.setText(angajat.getNume());

    }

    public void setService(Service service) {
        this.service = service;
    }
}
