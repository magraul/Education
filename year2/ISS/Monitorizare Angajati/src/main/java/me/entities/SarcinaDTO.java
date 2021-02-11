package me.entities;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.box.AlertBox;
import me.services.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class SarcinaDTO {
    private int idSarcina;
    private Service service;
    private String angajat;
    private String titlu;
    private String descriere;
    private int rezolvata;
    private String feedback;
    private Button deleteSarcina, feedbackSarcina, descriereSarcina;
    private Button details, finish, addFeedback;

    public Button getDetails() {
        return details;
    }

    public Button getFinish() {
        return finish;
    }

    public Button getAddFeedback() {
        return addFeedback;
    }

    public int isRezolvata() {
        return rezolvata;
    }

    public String getFeedback() {
        return feedback;
    }

    public Button getDescriereSarcina() {
        return descriereSarcina;
    }

    public SarcinaDTO(int idSarcina, String angajat, String titlu, String descriere, int rezolvata, String feedback, Service service) {
        this.angajat = angajat;
        this.idSarcina =idSarcina;
        this.titlu = titlu;
        this.rezolvata = rezolvata;
        this.descriere = descriere;
        this.feedback = feedback;
        deleteSarcina = new Button();
        feedbackSarcina = new Button();
        descriereSarcina = new Button();
        details = new Button();
        finish = new Button();
        addFeedback = new Button();
        this.service = service;
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("delete.png")));
            imageView.setFitHeight(18);
            imageView.setFitWidth(15);
            deleteSarcina.setGraphic(imageView);
            deleteSarcina.setOnAction(e-> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Stergere sarcina " + this.titlu);
                alert.setHeaderText("Conformare stergere");
                alert.setContentText("Sigur stergeti sarcina " + this.titlu + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                    service.deleteSarina(this.idSarcina, angajat);
                else alert.hide();


            });

            ImageView imageView1 = new ImageView(new Image(new FileInputStream("info.png")));
            imageView1.setFitHeight(18);
            imageView1.setFitWidth(15);
            feedbackSarcina.setGraphic(imageView1);
            feedbackSarcina.setOnAction(e-> AlertBox.display("Feedback", this.feedback));

            ImageView imageViewDescriereSarcina = new ImageView(new Image(new FileInputStream("edit.png")));
            imageViewDescriereSarcina.setFitHeight(18);
            imageViewDescriereSarcina.setFitWidth(15);
            descriereSarcina.setGraphic(imageViewDescriereSarcina);
            descriereSarcina.setOnAction(e-> service.loadFormDescriereSarcina(this.idSarcina, angajat, descriere));

            ImageView imageViewInfoSarcina = new ImageView(new Image(new FileInputStream("info.png")));
            imageViewInfoSarcina.setFitHeight(18);
            imageViewInfoSarcina.setFitWidth(18);
            details.setGraphic(imageViewInfoSarcina);
            details.setOnAction(e-> AlertBox.display("Detalii sarcina", this.descriere));

            ImageView imageViewFinish = new ImageView(new Image(new FileInputStream("finishhh.png")));
            imageViewFinish.setFitHeight(18);
            imageViewFinish.setFitWidth(18);
            finish.setGraphic(imageViewFinish);
            finish.setOnAction(e->service.finishSarcina(this.idSarcina, angajat));

            ImageView imageViewAddF = new ImageView(new Image(new FileInputStream("addd.png")));
            imageViewAddF.setFitHeight(18);
            imageViewAddF.setFitWidth(18);
            addFeedback.setGraphic(imageViewAddF);
            addFeedback.setOnAction(e -> service.addFeedbackSarcina(this.idSarcina, angajat));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Button getDeleteSarcina() {
        return deleteSarcina;
    }

    public Button getFeedbackSarcina() {
        return feedbackSarcina;
    }

    public void setFeedbackSarcina(Button feedbackSarcina) {
        this.feedbackSarcina = feedbackSarcina;
    }

    public void setDeleteSarcina(Button deleteSarcina) {
        this.deleteSarcina = deleteSarcina;
    }

    public void setRezolvata(int rezolvata) {
        this.rezolvata = rezolvata;
    }

    public String getAngajat() {
        return angajat;
    }

    public String getTitlu() {
        return titlu;
    }


}
