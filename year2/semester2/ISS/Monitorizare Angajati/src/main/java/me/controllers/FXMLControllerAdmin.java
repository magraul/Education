package me.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.box.AlertBox;
import me.entities.Angajat;
import me.entities.AngajatDTO;
import me.entities.Cerere;
import me.events.EvenimentSchimbare;
import me.observer.Observer;
import me.services.Service;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLControllerAdmin implements Initializable, EventHandler, Observer<EvenimentSchimbare> {
    private Service service;

    @FXML private TextField cauta;
    @FXML private javafx.scene.control.Button adauga, modifica, sterge, logOut;
    @FXML private TableView tabel;
    @FXML private TableColumn coloanaNume, coloanaUsername;

    ObservableList<AngajatDTO> modelTabel = FXCollections.observableArrayList();
    List<AngajatDTO> angajati;

    public void setService(Service service) {
        this.service = service;
        sincron();
        service.addObserver(this);
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() == adauga) {

            FXMLLoader loaderAdd = new FXMLLoader();
            loaderAdd.setLocation(getClass().getResource("/fxml/popUpAdaugaUser.fxml"));
            Parent root = null;
            try {
                root = loaderAdd.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FXMLControllerAdd ctrl =
                    loaderAdd.<FXMLControllerAdd>getController();
            ctrl.setService(service);

            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.setTitle("Adaugare user");
            window.setScene(scene);
            window.show();

        }

        if (event.getSource() == sterge) {
            AngajatDTO angajatDTO = (AngajatDTO) tabel.getSelectionModel().getSelectedItem();
            if (angajatDTO == null) {
                AlertBox.display("eror", "nu ati selectat un angajat!");
                return;
            }

            service.deleteAngajat(angajatDTO.getUsername());
        }

        if (event.getSource() == modifica) {
            AngajatDTO angajatDTO = (AngajatDTO) tabel.getSelectionModel().getSelectedItem();
            if (angajatDTO == null) {
                AlertBox.display("eror", "nu ati selectat un angajat!");
                return;
            }
            FXMLLoader loaderModifica = new FXMLLoader();
            loaderModifica.setLocation(getClass().getResource("/fxml/popUpModificaUser.fxml"));
            Parent root = null;
            try {
                root = loaderModifica.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FXMLControllerModifica ctrl =
                    loaderModifica.<FXMLControllerModifica>getController();
            ctrl.setService(service);
            ctrl.setTexts(angajatDTO);

            Scene scene = new Scene(root);
            Stage window = new Stage();
            window.setTitle("Modifica user");
            window.setScene(scene);
            window.show();

        }

        if (event.getSource() == logOut) {
            Stage windowNote = (Stage) ((javafx.scene.Node) logOut).getScene().getWindow();
            windowNote.hide();
        }


    }

    private void sincron() {
        modelTabel.clear();
        angajati = service.getAllAngajati();
        modelTabel.setAll(angajati);
        tabel.setItems(modelTabel);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adauga.setOnAction(this);
        modifica.setOnAction(this);
        sterge.setOnAction(this);

        logOut.setOnAction(this);

        cauta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter(); }
        });

        coloanaNume.setCellValueFactory(new PropertyValueFactory<AngajatDTO, String>("nume"));
        coloanaUsername.setCellValueFactory(new PropertyValueFactory<AngajatDTO, String>("username"));

        tabel.setRowFactory(tv -> new TableRow<AngajatDTO>() {
            @Override
            public void updateItem(AngajatDTO item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (item.aCerut()) {
                    setStyle("-fx-background-color: tomato;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void handleFilter() {
        modelTabel.setAll(angajati.stream().filter(x->x.getNume().contains(cauta.getText())).collect(Collectors.toList()));
    }

    @Override
    public void update(EvenimentSchimbare evenimentSchimbare) {
        if (evenimentSchimbare.getTip().toString().equals("USER_ADDED")){
            sincron();
        }
    }
}
