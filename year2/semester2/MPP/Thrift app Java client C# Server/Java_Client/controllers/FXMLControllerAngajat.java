package me.controllers;

import javafx.application.Platform;
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
import me.boxes.AlertBox;
import me.client.DonatoriListModel;
import me.entities.Angajat;
import me.entities.Donator;
import me.networking.dto.AngajatDTO;
import me.networking.dto.CazCaritabilDTO;
import me.teledon.CazDTO;
import me.teledon.TeledonService;
import me.teledonServices.ITeledonObserver;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;
import org.apache.thrift.TException;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLControllerAngajat implements Initializable, EventHandler, ITeledonObserver {

    private TeledonService.Client server;
    private Angajat user;



    private DonatoriListModel donatoriModel;

    ObservableList<CazCaritabilDTO> modelTabel = FXCollections.observableArrayList();

    @FXML
    Button doneaza;

    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem logOut;

    @FXML
    TextField numeDonator, adresa, nrTel, sumaDonata, search;

    @FXML
    TableView tabel;

    @FXML
    TableColumn descriere, sumaAdunata;

    @FXML
    ListView lista;

    public FXMLControllerAngajat() throws RemoteException {
        donatoriModel = new DonatoriListModel();
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == doneaza) {

            CazCaritabilDTO caz = (CazCaritabilDTO)tabel.getSelectionModel().getSelectedItem();
            if(caz == null) {
                AlertBox.display("eroare", "nu ati selectat un caz caritabil!");
                return;
            }

            try {
                CazDTO cazz = new CazDTO(caz.getDescriere(), caz.getSumaAdunata());
                server.donatieS(cazz, numeDonator.getText(), adresa.getText(), nrTel.getText(), Float.parseFloat(sumaDonata.getText()));
                numeDonator.setText("");
                adresa.setText("");
                nrTel.setText("");
                sumaDonata.setText("");
                search.setText("");
            } catch (TException teledonExeption) {
                teledonExeption.printStackTrace();
                AlertBox.display("eroare", teledonExeption.getMessage());
            }

        }

        if (event.getSource() == logOut) {
            logOUT();
            ((Node)menuBar).getScene().getWindow().hide();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doneaza.setOnAction(this);

        descriere.setCellValueFactory(new PropertyValueFactory<AngajatDTO, String>("descriere"));
        sumaAdunata.setCellValueFactory(new PropertyValueFactory<AngajatDTO, String>("sumaAdunata"));

        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter();
            }
        });

        lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (!lista.getSelectionModel().isEmpty()) {
                    String[] cuvinte = lista.getSelectionModel().getSelectedItems().get(0).toString().split(" {2}");
                    numeDonator.setText(cuvinte[0]);
                    adresa.setText(cuvinte[1]);
                    nrTel.setText(cuvinte[2]);
                }
            }
        });

        logOut.setOnAction(this);
    }

    private void handleFilter() {
        lista.getItems().setAll(donatoriModel.getDonatori().stream().filter(x->x.contains(search.getText())).collect(Collectors.toList()));
    }

    public void setComponente() {
        try {
            donatoriModel.setDonatori(server.getDonators());
            lista.getItems().setAll(donatoriModel.getDonatori());
            List<CazDTO> cazDTOS = server.getCauriDTO();
            List<CazCaritabilDTO> cazuri = cazDTOS.stream().map(x->new CazCaritabilDTO(x.descriere, (float)x.suma)).collect(Collectors.toList());
            modelTabel.setAll(cazuri);


        } catch (TException teledonExeption) {
            teledonExeption.printStackTrace();
        }
        tabel.setItems(modelTabel);

    }



    public void setServer(TeledonService.Client server) {

        this.server = server;
    }



    @Override
    public void donationMade(List<CazCaritabilDTO> caz) throws TeledonExeption {
        System.out.println("hello there");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                modelTabel.setAll(caz);
            }
        });

    }

    @Override
    public void donatorAdd(String d) throws TeledonExeption{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                donatoriModel.donatorAdded(d);
            }
        });

    }

    @Override
    public void donatorUpdate(Donator d) throws TeledonExeption {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                donatoriModel.donatorUpdate(d);
            }
        });

    }



    public void setUser(Angajat crtUser) {
        this.user = crtUser;
    }

    public void logOUT() {
//        try {
//         //   server.logout(user, this);
//            System.out.println("sa delogat " + user.getUsername());
//        }catch (TeledonExeption e){
//            System.out.println("log out error " + e);
//        }
    }
}
