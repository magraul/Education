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
import me.entities.Angajat;
import me.entities.SarcinaDTO;
import me.events.EvenimentSchimbare;
import me.observer.Observer;
import me.services.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLControllerAngajat implements Initializable, EventHandler, Observer<EvenimentSchimbare> {
    private Service service;
    private Angajat angajat;

    ObservableList<SarcinaDTO> modelTabelSarcini = FXCollections.observableArrayList();

    @FXML private TableView sarcini;
    @FXML private TableColumn descriere, info, finish, feedback;
    @FXML private TextField a;
    @FXML private Button logOut;

    @Override
    public void handle(Event event) {
        if (event.getSource() == logOut) {
            Stage window = (Stage) ((javafx.scene.Node) logOut).getScene().getWindow();
            service.plecareAngajat(this.angajat);
            window.hide();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOut.setOnAction(this);
        descriere.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, String>("titlu"));
        info.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, Button>("details"));
        finish.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, Button>("finish"));
        feedback.setCellValueFactory(new PropertyValueFactory<SarcinaDTO, Button>("addFeedback"));


        sarcini.setRowFactory(tv -> new TableRow<SarcinaDTO>() {
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

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        loadTablee();
    }

    private void loadTablee() {
        modelTabelSarcini.setAll(service.getSarciniDTOAngajat(this.angajat));
        sarcini.setItems(modelTabelSarcini);
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
        this.a.setText(angajat.getUsername());

    }

    @Override
    public void update(EvenimentSchimbare evenimentSchimbare) {
        if (evenimentSchimbare.getTip().toString().equals("SARCINA_COMPLETATA")){
            loadTablee();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("CERERE_ADD_FEEDBACK")){
            introduFeedbackForm(evenimentSchimbare);
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("SARCINA_NOUA")){
            loadTablee();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("SARCINA_DELETE") && evenimentSchimbare.getIdPersoana() == this.angajat.getId()){
            loadTablee();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Sarcina retrasa");
            alert.setContentText("Sarcina " + evenimentSchimbare.getSarcina().getTitlu() + " a fost retrasa");
            alert.showAndWait();
            return;
        }

        if (evenimentSchimbare.getTip().toString().equals("SARCINA_UPDATE") && evenimentSchimbare.getSarcina().getIdPersoana() == this.angajat.getId()){
            loadTablee();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Sarcina modificata");
            alert.setContentText("Sarcina " + evenimentSchimbare.getSarcina().getTitlu() + " a fost modificata");
            alert.showAndWait();
            return;
        }







    }

    private void introduFeedbackForm(EvenimentSchimbare evenimentSchimbare) {
        FXMLLoader loaderFeedback = new FXMLLoader();
        loaderFeedback.setLocation(getClass().getResource("/fxml/popUpFeedbackSarcina.fxml"));
        Parent root = null;
        try {
            root = loaderFeedback.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLControllerFeedback ctrl =
                loaderFeedback.<FXMLControllerFeedback>getController();
        ctrl.setService(service);
        ctrl.setData(evenimentSchimbare);

        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setTitle("Feedback");
        window.setScene(scene);

        window.show();
    }
}
