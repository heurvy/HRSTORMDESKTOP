/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import COM.HRSTORMDESKTOP.Config;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;

/**
 * FXML Controller class
 *
 * @author Moetez
 */
public class MainDashboardController implements Initializable {

    @FXML
    private Label WelcomeName;
    @FXML
    private Rectangle redirectUser;
    @FXML
    private Label Home;
    @FXML
    private Rectangle buttonrec;
    @FXML
    private Rectangle buttonfeuille;
    @FXML
    private Label labeluser;
    @FXML
    private Label labelrec;
    @FXML
    private Label labelfeuille;
    @FXML
    private Rectangle buttonconge;
    @FXML
    private Label labelconge;
    @FXML
    private Label Logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WelcomeName.setText(Config.UserStatic.getNom());
        if (Config.UserStatic.getRoles().equals("Employé")) {
            buttonrec.setOpacity(0);
            buttonrec.setDisable(true);
            labelrec.setOpacity(0);
            labelrec.setDisable(true);
            labeluser.setText("Réclamation");
            /*
            labeluser.setOpacity(0);
            labeluser.setDisable(true);
            redirectUser.setOpacity(0);
            redirectUser.setDisable(true);
             */
        }
        if (Config.UserStatic.getRoles().equals("Admin")) {
            buttonrec.setOpacity(0);
            labelrec.setOpacity(0);
            labelfeuille.setOpacity(0);
            buttonfeuille.setOpacity(0);
            buttonconge.setOpacity(0);
            labelconge.setOpacity(0);

            buttonrec.setDisable(true);
            labelrec.setDisable(true);
            labelfeuille.setDisable(true);
            buttonfeuille.setDisable(true);
            buttonconge.setDisable(true);
            labelconge.setDisable(true);
        }

    }

    @FXML
    private void redirectToUsers(MouseEvent event) {
        Parent root;
        try {
            if (Config.UserStatic.getRoles().equals("Employé")) {
                root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/user/AjoutReclamation.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/user/DisplayUser.fxml"));
            }
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) WelcomeName.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectHome(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/user/MainDashboard.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) WelcomeName.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToFeuille(MouseEvent event) {
        //Config.UserStatic.getNomPrenom()
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/feuilletemps/EvalViewFXML.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) WelcomeName.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToRec(MouseEvent event) {
        Parent root;
        try {
            System.out.println("Campagne");
            root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/recrutement/CampagneRecrutement.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) WelcomeName.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @FXML
    private void redirectToCon(MouseEvent event) {
                Parent root;

        try {
            if (Config.UserStatic.getRoles().equals("Employe")) {
                root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/conge/EmployeViewFXML.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/conge/CongeViewFXML.fxml"));
            }
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) WelcomeName.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void updateProfile(MouseEvent event) {
        Config.setUserManagedUpdate(Config.UserStatic);
        redirectToUpdate();
    }

    private void redirectToUpdate() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/user/ModifierUser.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) buttonrec.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToLandingPage(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LogOut");
        alert.setContentText("Logged out.");
        alert.show();
        Config.setUserStatic(null);
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/HRSTORMDESKTOP/views/user/LandingPage.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) buttonrec.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
