package me.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.entities.Sarcina;
import me.events.EvenimentSchimbare;
import me.services.Service;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLControllerFeedback implements EventHandler, Initializable {
    private Service service;
    private EvenimentSchimbare e;

    @FXML private TextField sarcina;
    @FXML private TextArea feedback;
    @FXML private Button send;

    @Override
    public void handle(Event event) {
        if (event.getSource() == send) {
            service.feedBack(e.getIdSarcina(), feedback.getText());

            Stage windowLogare = (Stage) ((javafx.scene.Node) send).getScene().getWindow();
            windowLogare.hide();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        send.setOnAction(this);
        sarcina.setEditable(false);
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setData(EvenimentSchimbare evenimentSchimbare) {
        this.e = evenimentSchimbare;
        Sarcina s = service.getSarcina(e.getIdSarcina());
        sarcina.setText(s.getTitlu());
        feedback.setText(s.getFeedback());
    }
}
