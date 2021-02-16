package me.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.box.AlertBox;
import me.entities.AngajatSefView;
import me.entities.Sarcina;
import me.events.EvenimentSchimbare;
import me.services.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLControllerTransmitere implements Initializable, EventHandler {
    private Service service;
    private int idPersoana = -1;
    private Sarcina sarcina;

    @FXML private TextField pentruCine, titlu;
    @FXML private TextArea desriere;
    @FXML private Button send;

    @Override
    public void handle(Event event) {
        if (event.getSource() == send) {
            if (titlu.getText().equals("") || desriere.getText().equals("")) {
                AlertBox.display("Eroare", "Nu ati completat!");
                return;
            }
            if (idPersoana != -1)
            service.addSarcina(idPersoana, titlu.getText(), desriere.getText());
            else {
                this.sarcina.setDescriere(desriere.getText());
                service.updateSarcina(this.sarcina);
            }
            Stage windowLogare = (Stage) ((javafx.scene.Node) send).getScene().getWindow();
            windowLogare.hide();
            if (-1 != idPersoana )
                AlertBox.display("Succes", "Sarcina transmisa cu succes!");
            else AlertBox.display("Succes", "Sarcina modificata cu succes!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        send.setOnAction(this);
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setPersoana(AngajatSefView angajatSefView) {
        this.idPersoana = service.getIdPersoanaByNameAndOra(angajatSefView.getNume(), angajatSefView.getOraSosire());
        this.pentruCine.setText(angajatSefView.getNume());
    }

    public void setData(EvenimentSchimbare evenimentSchimbare) {
        Sarcina s = service.getSarcina(evenimentSchimbare.getIdSarcina());
        pentruCine.setText(evenimentSchimbare.getNumeAngajat());
        titlu.setText(s.getTitlu());
        desriere.setText(s.getDescriere());
        this.sarcina =s;
    }
}
