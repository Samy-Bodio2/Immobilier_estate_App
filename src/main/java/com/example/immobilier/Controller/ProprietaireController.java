package com.example.immobilier.Controller;

import com.example.immobilier.Entity.Client;
import com.example.immobilier.Entity.Proprietaire;
import com.example.immobilier.Service.ProprietaireService;
import com.example.immobilier.SpringFXMLLoader;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ProprietaireController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private  ProprietaireService proprietaireService;
    @FXML
    private Pane paneProp;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLast;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfPhone;

    @FXML
    private TableView<Proprietaire> tableView;

    @FXML
    private TableColumn<Proprietaire,Integer> columnId;
    @FXML
    private TableColumn<Proprietaire,String> columnName;
    @FXML
    private TableColumn<Proprietaire,String> columnLast;
    @FXML
    private TableColumn<Proprietaire,String> columnEmail;
    @FXML
    private TableColumn<Proprietaire, String> columnPhone;


    @FXML
    private void handleClient(Event event) {
        navigateTo("Client");
    }

    @FXML
    private void handleAgent(Event event) {
        navigateTo("AgentView");
    }

    @FXML
    private void handleBiens(Event event) {
        navigateTo("BienImmoView");
    }

    @FXML
    private void handleContrat(Event event) {
        navigateTo("ContratView");
    }

    @FXML
    private void handleProp(Event event) {
        navigateTo("ProprietaireView");
    }

    @FXML
    private void handleTransaction(Event event) {
        navigateTo("TransactionView");
    }

    public void initialize(){
        loadProp();
    }

    private void navigateTo(String str) {
        try {
            FXMLLoader fxmlLoader = springFXMLLoader.load("/com/example/immobilier/view/"+str+".fxml");
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Page "+str);
            stage.setScene(scene);
            stage.show();

            Stage oldStage = (Stage) paneProp.getScene().getWindow();
            oldStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page "+str);
        }
    }

    private void loadProp(){
        columnId.setCellValueFactory(new PropertyValueFactory<Proprietaire,Integer>("idPropritaire"));
        columnName.setCellValueFactory(new PropertyValueFactory<Proprietaire,String>("nom"));
        columnLast.setCellValueFactory(new PropertyValueFactory<Proprietaire,String>("prenom"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Proprietaire,String>("telephone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Proprietaire,String>("email"));

        ObservableList<Proprietaire> Props = FXCollections.observableArrayList(proprietaireService.getAllProprietaires());
        tableView.setItems(Props);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void createProprietaire(ActionEvent event) {
        try{
            String firstName = tfName.getText();
            String lastName = tfLast.getText();
            String email = tfEmail.getText();
            String phone = tfPhone.getText();

            Proprietaire prop = Proprietaire.builder()
                    .nom(firstName)
                    .prenom(lastName)
                    .telephone(phone)
                    .email(email)
                    .build();

            proprietaireService.addProprietaire(prop);
            showAlert(Alert.AlertType.INFORMATION, "Creation réussie","prop");
            tfName.setText("");
            tfLast.setText("");
            tfEmail.setText("");
            tfPhone.setText("");
            initialize();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
