/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.controllers.user;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import COM.HRSTORMDESKTOP.Config;
import COM.HRSTORMDESKTOP.models.user.User;
import COM.HRSTORMDESKTOP.services.user.ImpServiceUser;

/**
 * FXML Controller class
 *
 * @author Achref
 */
public class DisplayUserController implements Initializable {

    @FXML
    private TableView<User> ListDisplay;
    @FXML
    private TableColumn<String, User> Nom;
    @FXML
    private TableColumn<String, User> Prenom;
    @FXML
    private TableColumn<String, User> Password;
    @FXML
    private TableColumn<Date, User> Nomsociete;
    @FXML
    private TableColumn<String, User> Email;
    private ImpServiceUser impsu = new ImpServiceUser();
    private ObservableList<User> dataList = FXCollections.observableArrayList();
    @FXML
    private TextField SearchInput;
    @FXML
    private Label adduserLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //User statique
        //Societe s = new Societe();
        //s.setIdSociete(3);
        //Config.UserStatic.setSociete(s);
        //User statique

        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Nomsociete.setCellValueFactory(new PropertyValueFactory<>(""));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        if (Config.UserStatic.getRoles().equals("HRResponsable")) {
            System.out.println("Showed societe");
          //  for (User p : impsu.GetByNomsociete(Config.UserStatic)) {
          //      ListDisplay.getItems().add(p);
          //  }
        } else {
            User u = new User();
            u.setIs_verified(true);
            System.out.println("here showed resps");
            for (User p : impsu.GetResponsables(u)) {
                //User u = new User(p.getNomPrenom(), p.getAdresse(), p.getCin(), p.getDateNaissance(), p.getEmail());
                ListDisplay.getItems().add(p);
            }

        }
       
     /*   if (ListDisplay.getItems().size() >= p.getNbrUsers() && Config.UserStatic.getRoles().equals("Responsable")) {
            adduserLabel.setDisable(true);
            adduserLabel.setOpacity(0);
        }*/
        if (Config.UserStatic.getRoles().equals("Admin")) {
            adduserLabel.setDisable(true);
            adduserLabel.setOpacity(0);
        }
        addUpdateButton();
        addDeleteButton();
        Search();

    }

    private void addUpdateButton() {
        TableColumn<User, Void> colBtn = new TableColumn("Modifier");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.applyCss();
                        btn.getStyleClass().add("buttongreen");

                        btn.setOnAction((ActionEvent event) -> {

                            User data = getTableView().getItems().get(getIndex());
                            User retrievedUser = impsu.GetByMail(data.getEmail());
                            Config.setUserManagedUpdate(retrievedUser);

                            redirectToUpdate();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);

                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        ListDisplay.getColumns().add(colBtn);

    }

    private void Search() {
        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        Nomsociete.setCellValueFactory(new PropertyValueFactory<>("Nomsociete"));
        Email.setCellValueFactory(new PropertyValueFactory<>("Email"));

        if (Config.UserStatic.getRoles().equals("Responsable")) {
            System.out.println("Showed societe");
            for (User p : impsu.GetByRespSociete(Config.UserStatic)) {
                //User u = new User(p.getNomPrenom(), p.getAdresse(), p.getCin(), p.getDateNaissance(), p.getEmail());
                dataList.add(p);
            }
        } else {
            User u = new User();
            u.setIs_verified(true);
            System.out.println("here showed resps");
            for (User p : impsu.GetResponsables(u)) {
                //User u = new User(p.getNomPrenom(), p.getAdresse(), p.getCin(), p.getDateNaissance(), p.getEmail());
                dataList.add(p);
            }

        }

        FilteredList<User> filteredData = new FilteredList<>(dataList, b -> true);
        SearchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getNomsociete().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ListDisplay.comparatorProperty());
        ListDisplay.setItems(sortedData);
    }

    private void addDeleteButton() {
        TableColumn<User, Void> colBtn = new TableColumn("Supprimer");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Supprimer");

                    {
                        btn.applyCss();
                        btn.getStyleClass().add("buttonred");

                        btn.setOnAction((ActionEvent event) -> {

                            User data = getTableView().getItems().get(getIndex());
                            User retrievedUser = impsu.GetByMail(data.getEmail());
                            if (retrievedUser.getId() != Config.UserStatic.getId()) {
                                System.out.println(data.getId() + " " + Config.UserStatic.getId());
                                Config.setUserManagedDelete(retrievedUser);
                                impsu.Delete(Config.UserManagedDelete);
                                reloadPage();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Suppression");
                                alert.setContentText("Vous ne pouvez pas supprimer ce compte.");
                                alert.show();
                            }
                        });
                        if (Config.UserStatic.getRoles().equals("Admin")){
                            btn.setOpacity(0);
                            btn.setDisable(true);
                        }
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);

                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        ListDisplay.getColumns().add(colBtn);

    }

    
    private void reloadPage() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/masterhrdesktopv2/views/user/DisplayUser.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/masterhrdesktopv2/bootstrap.css");

            Stage stage = (Stage) ListDisplay.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void redirectToUpdate() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/masterhrdesktopv2/views/user/ModifierUser.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/masterhrdesktopv2/bootstrap.css");

            Stage stage = (Stage) ListDisplay.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }



    @FXML
    private void redirectHome(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/masterhrdesktopv2/views/user/MainDashboard.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/masterhrdesktopv2/bootstrap.css");

            Stage stage = (Stage) ListDisplay.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectAjoutUser(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/masterhrdesktopv2/views/user/AjoutUser.fxml"));
            Scene c = new Scene(root);
            c.getStylesheets().add("/masterhrdesktopv2/bootstrap.css");

            Stage stage = (Stage) ListDisplay.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
