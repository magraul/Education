package me;

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
import me.teledonServices.ITeledonObserver;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;
import org.controlsfx.control.textfield.TextFields;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLControllerAngajat extends UnicastRemoteObject implements Initializable, EventHandler, ITeledonObserver, Serializable {

    private ITeledonServices server;
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

                server.donatieS(caz, numeDonator.getText(), adresa.getText(), nrTel.getText(), Float.parseFloat(sumaDonata.getText()));
              numeDonator.setText("");
              adresa.setText("");
              nrTel.setText("");
              sumaDonata.setText("");
              search.setText("");
        } catch (TeledonExeption teledonExeption) {
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
            modelTabel.setAll(server.getCauriDTO());
           // TextFields.bindAutoCompletion(search, getNumeDonators());

        } catch (TeledonExeption teledonExeption) {
            teledonExeption.printStackTrace();
        }
       tabel.setItems(modelTabel);

    }

    private List<String> getNumeDonators() {
        List<String> rez = new ArrayList<>();
        try {
            List<String> donatori = server.getDonators();
            for (String linie : donatori){
                String[] elems = linie.split(" {2}");
                rez.add(elems[0]);
            }

        } catch (TeledonExeption exeption) {
            exeption.printStackTrace();
        }

        return rez;
    }



    public void setServer(ITeledonServices server) {
        this.server = server;
    }



    @Override
    public void donationMade(CazCaritabilDTO caz) throws TeledonExeption {
        modelTabel.removeIf(cazCaritabilDTO -> cazCaritabilDTO.getDescriere().equals(caz.getDescriere()));
        modelTabel.add(caz);
    }

    @Override
    public void donatorAdd(String d) throws TeledonExeption, RemoteException {
        donatoriModel.donatorAdded(d);
    }

    @Override
    public void donatorUpdate(Donator d) throws TeledonExeption, RemoteException {
        donatoriModel.donatorUpdate(d);
    }



    public void setUser(Angajat crtUser) {
        this.user = crtUser;
    }

    public void logOUT() {
        try {
            server.logout(user, this);
            System.out.println("sa delogat " + user.getUsername());
        }catch (TeledonExeption e){
            System.out.println("log out error " + e);
        }
    }
}
