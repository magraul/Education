package me;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.controllers.FXMLControllerLogare;
import me.entities.Angajat;
import me.hibernate.HibernateUtil;
import me.repositories.AngajatiRepository;
import me.repositories.SarciniRepository;
import me.services.Service;
import me.validators.AngajatiValidator;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.module.Configuration;

public class Main extends Application {
    public static void main(String[] args) {
        
        launch(args);
    }

    @Override public void start(Stage window) throws Exception {


        Service service = new Service(new AngajatiRepository(), new SarciniRepository(), new AngajatiValidator());

        for (int i=0;i<4;i++) {
            Stage s = new Stage();
            FXMLLoader loaderLogare = new FXMLLoader();
            loaderLogare.setLocation(getClass().getResource("/fxml/logareView.fxml"));
            Parent root = loaderLogare.load();

            FXMLControllerLogare ctrl =
                    loaderLogare.<FXMLControllerLogare>getController();
            ctrl.setService(service);

            Scene scene = new Scene(root);
            s.setScene(scene);
            s.show();
        }
    }

}
