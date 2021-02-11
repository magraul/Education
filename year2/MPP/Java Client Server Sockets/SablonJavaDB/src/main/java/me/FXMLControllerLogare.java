package me;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.boxes.AlertBox;
import me.entities.Angajat;
import me.repositories.AngajatiDBRepository;
import me.repositories.CazuriDBRepository;
import me.repositories.DonatiiDBRepository;
import me.repositories.DonatoriDBRepository;
import me.teledonServer.Service;
import me.teledonServices.ITeledonServices;
import me.teledonServices.TeledonExeption;
import me.validators.ValidatorCazuri;
import me.validators.ValidatorDonatii;
import me.validators.ValidatorDonatori;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class FXMLControllerLogare implements Initializable, EventHandler {
    private Properties serverProps = null;

    private ITeledonServices server;
    private FXMLControllerAngajat angajatCtrl;
    private Angajat crtUser;
    Parent mainTeledonParent;


    {
        this.serverProps = new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            serverProps.list(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 //   Service service = new Service(new AngajatiDBRepository(serverProps), new CazuriDBRepository(serverProps), new DonatiiDBRepository(serverProps), new DonatoriDBRepository(serverProps), new ValidatorCazuri(), new ValidatorDonatii(), new ValidatorDonatori());
 //   Map<String, String> angajatiMap = new HashMap<>();

    @FXML
    Button logIn;

    @FXML
    TextField username, parola;


    @Override
    public void handle(Event event) {
        if (event.getSource() == logIn) {
            if (username.getText().length() == 0 || parola.getText().length() == 0) {
                AlertBox.display("eroare", "toate fieldurile sunt obligatorii");
                return;
            }
            if (username.getText().equals("admin")) {
                //view admin
                try {
                    FXMLLoader loaderAdmin = new FXMLLoader();
                    loaderAdmin.setLocation(getClass().getResource("/fxml/adminView.fxml"));

                    Parent viewLogare = loaderAdmin.load();

                    Stage windowNote = (Stage) ((Node) logIn).getScene().getWindow();
                    windowNote.hide();

                    Scene scene = new Scene(viewLogare);
                    Stage window = new Stage();
                    window.setTitle("Administrator");
                    window.setScene(scene);

                    FXMLControllerAdmin ctrl = loaderAdmin.getController();
                    ctrl.setServer(this.server);
                    server.login(new Angajat("","", "", "admin", "parola"), angajatCtrl);
                    ctrl.setComponente();
                    window.show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    AlertBox.display("eroare", e.getMessage());
                }

            } else {
                //logare ca angajat
                //if (angajatiMap.get(username.getText()) != null && BCrypt.checkpw(parola.getText(), angajatiMap.get(username.getText()))) {
                //logare cu succes acgajat
                crtUser = new Angajat("", "", "", username.getText(), parola.getText());
                angajatCtrl.setUser(crtUser);
                try {
                    server.login(crtUser, angajatCtrl);
                    server.check(crtUser);

                    // FXMLLoader loaderAngajat = new FXMLLoader();
                    //   loaderAngajat.setLocation(getClass().getResource("/fxml/noteView.fxml"));

                    //  Parent viewAngajat = loaderAngajat.load();

                    Stage stage = new Stage();
                    stage.setTitle("teledon window for " + crtUser.getUsername());
                    stage.setScene(new Scene(mainTeledonParent));
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            angajatCtrl.logOUT();
                            System.exit(0);
                        }
                    });
                    stage.show();

                    Stage windowLogare = (Stage) ((Node) logIn).getScene().getWindow();
                    windowLogare.hide();

                    //FXMLControllerAngajat ctrl = loaderAngajat.getController();

                    angajatCtrl.setComponente();

                } catch (TeledonExeption e) {
                    angajatCtrl.logOUT();
                    System.out.println(e.getMessage());
                    AlertBox.display("eroare", e.getMessage());
                }

              /*  } else {

                }*/
                // }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logIn.setOnAction(this);
    }

    private void incarcaDictionare() {
        //angajatiMap = service.getDataAutentificareAngajati();
    }

    public void setComponente() {
        incarcaDictionare();
    }

    public void setServer(ITeledonServices server) {
        this.server = server;
    }

    public void setAngajatController(FXMLControllerAngajat controllerAngajat) {
        this.angajatCtrl = controllerAngajat;
    }

    public void setParent(Parent croot) {
        this.mainTeledonParent = croot;
    }
}
