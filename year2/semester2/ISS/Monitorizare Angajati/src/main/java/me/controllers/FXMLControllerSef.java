package me.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.entities.AngajatDTO;
import me.entities.AngajatSefView;
import me.entities.Sarcina;
import me.entities.SarcinaDTO;
import me.events.EvenimentSchimbare;
import me.observer.Observer;
import me.services.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLControllerSef implements Initializable, EventHandler, Observer<EvenimentSchimbare> {
    private Service service;
    @FXML private Button logOut, trimiteSarcina;
    @FXML private TextField persoana;

    @FXML
    TableView tabelAngajati, tabelSarcini;

    @FXML
    TableColumn numeAngajat, oraSosire, angajatTabel2, titluSarcina, info, delete, descriereSarcina;

    ObservableList<AngajatSefView> modelTabelAngajati = FXCollections.observableArrayList();
    ObservableList<SarcinaDTO> modelTabelSarcini = FXCollections.observableArrayList();

    @Override
    public void handle(Event event) {
        if (event.getSource() == trimiteSarcina) {
            AngajatSefView angajatSefView = (AngajatSefView) tabelAngajati.getSelectionModel().getSelectedItem();

            FXMLLoader loaderTransmitere = new FXMLLoader();
            loaderTransmitere.setLocation(getClass().getResource("/fxml/popUpSarcina.fxml"));
            Parent root = null;
            try {
                root = loaderTransmitere.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FXMLControllerTransmitere ctrl =
                    loaderTransmitere.<FXMLControllerTransmitere>getController();
            ctrl.setService(service);
            ctrl.setPersoana(angajatSefView);

            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.setTitle("Detaii sarcina");
            window.setScene(scene);

            window.show();
        }

        if (event.getSource() == logOut) {
            Stage window = (Stage) ((javafx.scene.Node) logOut).getScene().getWindow();
            window.hide();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trimiteSarcina.setOnAction(this);
        logOut.setOnAction(this);
        numeAngajat.setCellValueFactory(new PropertyValueFactory<AngajatSefView, String>("nume"));
        oraSosire.setCellValueFactory(new PropertyValueFactory<AngajatSefView, String>("oraSosire"));

        angajatTabel2.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, String>("angajat"));
        titluSarcina.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, String>("titlu"));
        delete.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, Button>("deleteSarcina"));
        info.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, Button>("feedbackSarcina"));
        descriereSarcina.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, Button>("descriereSarcina"));


        tabelSarcini.setRowFactory(tv -> new TableRow<SarcinaDTO>() {
            @Override
            public void updateItem(SarcinaDTO item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (item.isRezolvata() == 0) {
                    setStyle("-fx-background-color: yellow;");
                }
                else if(item.isRezolvata() == 1){
                    setStyle("-fx-background-color: limegreen");
                }
                else {
                    setStyle("");
                }
            }
        });
    }

    @Override
    public void update(EvenimentSchimbare evenimentSchimbare) {
        if (evenimentSchimbare.getTip().toString().equals("SARCINA_DELETE")){
            loadSarciniDate();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("SARCINA_NOUA")) {
            loadSarciniDate();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("LOAD_FORM_DESCRIERE")) {
            loadFormDescriereSarcina(evenimentSchimbare);
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("ANGAJAT_LOGAT")) {
            loadPersoaneLogate();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("SARCINA_COMPLETATA")) {
            loadSarciniDate();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("FEEDBACK_ADDED")) {
            loadSarciniDate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Feedback nou");
            alert.setContentText("Sarcina " + evenimentSchimbare.getSarcina().getTitlu() + " a primit feedback de la " +
                    service.getAngajatById(evenimentSchimbare.getSarcina().getIdPersoana()).getNume());
            alert.showAndWait();
            return;
        }







    }

    private void loadFormDescriereSarcina(EvenimentSchimbare evenimentSchimbare) {
        FXMLLoader loaderTransmitere = new FXMLLoader();
        loaderTransmitere.setLocation(getClass().getResource("/fxml/popUpSarcina.fxml"));
        Parent root = null;
        try {
            root = loaderTransmitere.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLControllerTransmitere ctrl =
                loaderTransmitere.<FXMLControllerTransmitere>getController();
        ctrl.setService(service);
        ctrl.setData(evenimentSchimbare);

        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setTitle("Detaii sarcina");
        window.setScene(scene);

        window.show();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadComponents();
    }

    public void setPersoana(String text) {
        persoana.setText(text);
    }

    private void loadComponents() {
        loadPersoaneLogate();
        loadSarciniDate();
    }

    private void loadSarciniDate() {
        modelTabelSarcini.setAll(service.getSarciniDTO());
        tabelSarcini.setItems(modelTabelSarcini);
    }

    private void loadPersoaneLogate() {
        modelTabelAngajati.setAll(service.getAngajatiLogati());
        tabelAngajati.setItems(modelTabelAngajati);
    }
}
