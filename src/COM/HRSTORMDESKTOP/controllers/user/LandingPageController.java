/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Moetez
 */
public class LandingPageController implements Initializable {

    /*@FXML
    private Button chat;*/
    @FXML
    private Button signin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   /* @FXML
    private void RedirectToChat(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/user/AchatPack.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/COM/HRSTORMDESKTOP/bootstrap.css");

            Stage stage = (Stage) purchase.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    } */

    @FXML
    private void RedirectToLogin(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/COM/HRSTORMDESKTOP/views/user/Login.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/COM/HRSTORMDESKTOP/bootstrap.css");

            //Stage stage = (Stage) purchase.getScene().getWindow();
          //  stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
}
