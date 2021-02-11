package me;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.boxes.AlertBox;
import me.entities.Angajat;
import me.entities.CazCaritabil;
import me.entities.Donatie;
import me.entities.Donator;
import me.networking.dto.AngajatDTO;
import me.networking.dto.CazCaritabilDTO;
import me.events.EvenimentSchimbare;
import me.events.TipEveniment;
import me.networking.dto.DonatieDTO;
import me.observer.Observer;
import me.teledonServer.Service;
import me.teledonServices.ITeledonObserver;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;
import org.controlsfx.control.textfield.TextFields;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLControllerAngajat implements Initializable, EventHandler, ITeledonObserver {

    private ITeledonServices server;
    private Angajat user;

    private Donator donatorSelectat = null;

    private String numeVechi = "";
    private String adresaVeche = "";
    private String nrTelVechi = "";

    List<String> randuriDonatori = null;


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

    @Override
    public void handle(Event event) {
      if (event.getSource() == doneaza) {

            CazCaritabilDTO caz = (CazCaritabilDTO)tabel.getSelectionModel().getSelectedItem();
            if(caz == null) {
                AlertBox.display("eroare", "nu ati selectat un caz caritabil!");
                return;
            }
          //Donatie d = new Donatie(Float.parseFloat(sumaDonata.getText()), 2,2);
          try {


              //server.donatie(d);
              Integer cazId = server.getCazId(caz);
          //
              numeVechi = numeDonator.getText();
              nrTelVechi = nrTel.getText();
              adresaVeche = adresa.getText();


              server.commitDonatori(numeVechi, nrTelVechi, adresaVeche);
              if (donatorSelectat == null)
                donatorSelectat = server.getDonator(numeVechi);

              server.donatieS(Float.parseFloat(sumaDonata.getText()), donatorSelectat.getId(), cazId);
                donatorSelectat = null;





//
//

              numeDonator.setText("");
              adresa.setText("");
              nrTel.setText("");
              sumaDonata.setText("");
              search.setText("");
              //server.donatieS(new DonatieDTO(cazId, numeDonator.getText(), adresa.getText(),nrTel.getText(),Float.parseFloat(sumaDonata.getText())));
             // server.donatieS(cazId, numeDonator.getText(), adresa.getText(),nrTel.getText(),Float.parseFloat(sumaDonata.getText()));
          } catch (TeledonExeption teledonExeption) {
              teledonExeption.printStackTrace();
              AlertBox.display("eroare", teledonExeption.getMessage());
          }
          ////////

            //try {
            //    service.donatie(cazId, numeDonator.getText(), adresa.getText(),nrTel.getText(),Float.parseFloat(sumaDonata.getText()));
           // } catch (ValidatorException e) {
           //
            //}
        }

      if (event.getSource() == logOut) {
          logOUT();
          ((Node)menuBar).getScene().getWindow().hide();
//          try {
//              FXMLLoader loaderLogare = new FXMLLoader();
//              loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
//
//              Parent viewLogare = loaderLogare.load();
//
//              Stage windowNote = (Stage) ((Node) menuBar).getScene().getWindow();
//              windowNote.hide();
//
//              Scene scene = new Scene(viewLogare);
//              Stage window = new Stage();
//              window.setTitle("Logare");
//              window.setScene(scene);
//
//              FXMLControllerLogare ctrl = loaderLogare.getController();
//              ctrl.setComponente();
//              window.show();
//
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
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
                    try {
                        donatorSelectat = server.getDonator(numeDonator.getText());
                    } catch (TeledonExeption exeption) {
                        exeption.printStackTrace();
                    }
                }
            }
        });

        logOut.setOnAction(this);
    }

    private void handleFilter() {
        lista.getItems().setAll(randuriDonatori.stream().filter(x->x.contains(search.getText())).collect(Collectors.toList()));
    }

    public void setComponente() {
        try {
            randuriDonatori = server.getDonators();
            lista.getItems().setAll(randuriDonatori);
            modelTabel.setAll(server.getCauriDTO());
            //TextFields.bindAutoCompletion(search, getNumeDonators());

        } catch (TeledonExeption teledonExeption) {
            teledonExeption.printStackTrace();
        }

        // List<CazCaritabilDTO> d = service.getAllCazuriDTO();
     //   modelTabel.setAll(service.getAllCazuriDTO());
       tabel.setItems(modelTabel);
      //  service.addObserver(this);

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


    public void update(EvenimentSchimbare evenimentSchimbare) {
        if (evenimentSchimbare.getTip().compareTo(TipEveniment.DONATIE) == 0) {
         ////   modelTabel.setAll(service.getAllCazuriDTO());

           //// lista.getItems().setAll(service.getRanduriDonatori());
        }
    }

    public void setServer(ITeledonServices server) {
        this.server = server;
    }


    private void doIt(List<String> donatori, List<CazCaritabilDTO> cazuri){
        new Thread(()->{
            lista.getItems().setAll(donatori);
            modelTabel.setAll(cazuri);
        }).start();

    }
    @Override
    public void donationMade(List<String> donatori, List<CazCaritabilDTO> cazuri) throws TeledonExeption {
        System.out.println("am intrat in donation made");
        System.out.println(donatori);
        System.out.println(cazuri);
        modelTabel.setAll(cazuri);

        //actualizam lista
       // lista.getItems().setAll(donatori);

    }

    @Override
    public void commitDonatori(List<String> donatori) throws TeledonExeption {
       Platform.runLater(new Runnable() {
           @Override
           public void run() {
               randuriDonatori = donatori;
               lista.getItems().setAll(donatori);
              // TextFields.bindAutoCompletion(search, parseDomatori(donatori));
           }
       });

    }

    private List<String> parseDomatori(List<String> donatori) {
        List<String> rez = new ArrayList<>();

            for (String linie : donatori){
                String[] elems = linie.split(" {2}");
                rez.add(elems[0]);
            }
        return rez;
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
