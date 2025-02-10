package com.example.immobilier.Controller;

import com.example.immobilier.Entity.Agent;
import com.example.immobilier.Entity.Client;
import com.example.immobilier.Repository.ClientRepository;
import com.example.immobilier.Service.ClientService;
import com.example.immobilier.SpringFXMLLoader;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Controller
public class ClientController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private ClientService clientService;
    @FXML
    private Pane paneClient;
    @FXML
    private TextField tfClientName;
    @FXML
    private TextField tfClientEmail;
    @FXML
    private TextField tfClientPhone;

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TableColumn<Client,Integer> columnId;
    @FXML
    private TableColumn<Client,String> columnName;
    @FXML
    private TableColumn<Client,String> columnEmail;
    @FXML
    private TableColumn<Client, String> columnPhone;

    public void initialize() {
        loadClients();
    }


    private void loadClients(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        ObservableList<Client> clients = FXCollections.observableArrayList(clientService.getAllClients());
        tableView.setItems(clients);
    }

    public ClientController() {
    }

    @FXML
    private void handleClient(ActionEvent event) {
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

    private void navigateTo(String str) {
        try {
            FXMLLoader fxmlLoader = springFXMLLoader.load("/com/example/immobilier/view/"+str+".fxml");
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Page "+str);
            stage.setScene(scene);
            stage.show();

            Stage oldStage = (Stage) paneClient.getScene().getWindow();
            oldStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page "+str);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void createClient(ActionEvent event){
        try{
            String clientName = tfClientName.getText();
            String clientEmail = tfClientEmail.getText();
            String clientPhone = tfClientPhone.getText();

            Client client = Client.builder()
                    .nom(clientName)
                    .email(clientEmail)
                    .telephone(clientPhone)
                    .build();
            clientService.addClient(client);
            showAlert(Alert.AlertType.INFORMATION, "Creation réussie","client");
            initialize();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
