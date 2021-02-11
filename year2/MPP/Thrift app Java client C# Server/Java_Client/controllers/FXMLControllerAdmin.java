package me.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.entities.Angajat;
import me.entities.Donator;
import me.networking.dto.AngajatDTO;
import me.networking.dto.CazCaritabilDTO;
import me.teledonServices.ITeledonObserver;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLControllerAdmin implements Initializable, EventHandler, ITeledonObserver {

    ObservableList<AngajatDTO> modelTabel = FXCollections.observableArrayList();

    @FXML
    Button logOut, inregistrare;

    @FXML
    TableView tabel;

    @FXML
    TableColumn nume, username;

    @FXML
    TextField angajat, usernameAngajat;

    @FXML
    PasswordField parola;

    ITeledonServices server;

    List<AngajatDTO> angajatDTOS;

    Angajat crtUser;

    @Override
    public void handle(Event event) {
        if (event.getSource() == logOut) {
            try {
//                FXMLLoader loaderLogare = new FXMLLoader();
//                loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
//
//                Parent viewLogare = loaderLogare.load();

                Stage windowNote = (Stage) ((Node) event.getSource()).getScene().getWindow();
                server.logout(crtUser, this);
                windowNote.hide();

//
//                Scene scene = new Scene(viewLogare);
//                Stage window = new Stage();
//                window.setTitle("Logare");
//                window.setScene(scene);
//
//                FXMLControllerLogare ctrl = loaderLogare.getController();
//                ctrl.setComponente();
//                window.show();

            } catch ( TeledonExeption e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == inregistrare) {
            AngajatDTO i = new AngajatDTO(angajat.getText(), usernameAngajat.getText());
            //String pass = server.encode(parola.getText());

            try {
                server.updateAngajat(i, parola.getText());
                angajatDTOS = server.getAngajati();
                modelTabel.setAll(server.getAngajati());
            } catch (TeledonExeption teledonExeption) {
                teledonExeption.printStackTrace();
            }
            angajat.setText("");
            usernameAngajat.setText("");
            parola.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOut.setOnAction(this);
        inregistrare.setOnAction(this);

        angajat.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter(); }
        });

        nume.setCellValueFactory(new PropertyValueFactory<AngajatDTO, String>("nume"));
        username.setCellValueFactory(new PropertyValueFactory<AngajatDTO, String>("username"));
    }

    private void handleFilter() {
        modelTabel.setAll(angajatDTOS.stream().filter(x->x.getNume().contains(angajat.getText())).collect(Collectors.toList()));
    }

    public void setComponente() {
        try {
            crtUser = new Angajat("", "", "", "admin", "parola");
//            server.login(crtUser, this);
            angajatDTOS = server.getAngajati();
            modelTabel.setAll(angajatDTOS);

        } catch (TeledonExeption teledonExeption) {
            teledonExeption.printStackTrace();
        }
        tabel.setItems(modelTabel);
    }

    public void setServer(ITeledonServices server) {
        this.server = server;
    }





    @Override
    public void donationMade(List<CazCaritabilDTO> caz) throws TeledonExeption {

    }

    @Override
    public void donatorAdd(String d) throws TeledonExeption {

    }

    @Override
    public void donatorUpdate(Donator d) throws TeledonExeption {

    }
}
