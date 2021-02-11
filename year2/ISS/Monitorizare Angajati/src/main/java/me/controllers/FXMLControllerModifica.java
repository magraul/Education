package me.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.box.AlertBox;
import me.entities.Angajat;
import me.entities.AngajatDTO;
import me.services.Service;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLControllerModifica implements Initializable, EventHandler {
    private Service service;
    private int id;

    @FXML
    private Button submit, close;
    @FXML
    private TextField nume, adresa, nrTel, username, parola;

    @Override
    public void handle(Event event) {
        if (event.getSource() == close) {
            Stage windowNote = (Stage) ((javafx.scene.Node) close).getScene().getWindow();
            windowNote.hide();
        }

        if (event.getSource() == submit) {
            try {
                service.modificaUser(id, nume.getText(), adresa.getText(), nrTel.getText(), username.getText(), parola.getText());
                Stage windowNote = (Stage) ((javafx.scene.Node) close).getScene().getWindow();
                windowNote.hide();

            } catch (ValidationException e) {
                AlertBox.display("eroare", e.getMessage());
            }

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit.setOnAction(this);
        close.setOnAction(this);
    }

    public void setTexts(AngajatDTO angajatDTO) {
        Angajat angajat = service.getByUsername(angajatDTO.getUsername());
        this.id = angajat.getId();
        nume.setText(angajat.getNume());
        adresa.setText(angajat.getAdresa());
        nrTel.setText(angajat.getNrTel());
        username.setText(angajat.getUsername());

    }

    public void setService(Service service) {
        this.service = service;
    }
}
