package com.example.immobilier.Controller;

import com.example.immobilier.Entity.BienImmobilier;
import com.example.immobilier.Entity.Client;
import com.example.immobilier.Entity.Contrat;
import com.example.immobilier.Entity.ExtraType.CategorieBien;
import com.example.immobilier.Entity.ExtraType.TypeContrat;
import com.example.immobilier.Entity.Proprietaire;
import com.example.immobilier.Service.BienService;
import com.example.immobilier.Service.ClientService;
import com.example.immobilier.Service.ContratService;
import com.example.immobilier.Service.ProprietaireService;
import com.example.immobilier.SpringFXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class ContractController {

    @Autowired
    private ProprietaireService proprietaireService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BienService bienService;

    @FXML
    private Pane paneContrat;
    @Autowired
    private ContratService contratService;
    @FXML
    private ComboBox cbType;
    @FXML
    private TextField tfMontant;
    @FXML
    private ComboBox cbProp;
    @FXML
    private ComboBox cbClient;
    @FXML
    private ComboBox cbImmo;

    @FXML
    private TableColumn<Contrat,Integer> columnId;
    @FXML
    private TableColumn<Contrat, TypeContrat> columnType;
    @FXML
    private TableColumn<Contrat,Double> columnMontant;
    @FXML
    private TableColumn<Contrat,Double> columnImmobilier;
    @FXML
    private TableColumn<Contrat,String> columnClient;
    @FXML
    private TableColumn<Contrat,String> columnProp;
    @FXML
    private TableView<Contrat> tableView;
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    public ContractController(){
    }

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
        LoadType();
        LoadTableView();
    }

    private void LoadType(){
        cbType.getItems().addAll(TypeContrat.values());
        List<String> nom = proprietaireService.getAllProprietaires().stream().map(Proprietaire::getNom).toList();
        cbProp.getItems().addAll(nom);
        List<String> nomC = clientService.getAllClients().stream().map(Client::getNom).toList();
        cbClient.getItems().addAll(nomC);
        List<Integer> idsB = bienService.getAllBiens().stream().map(BienImmobilier::getIdBien).toList();
        cbImmo.getItems().addAll(idsB);
    }
    
    private void LoadTableView(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idContrat"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("typeContrat"));
        columnMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        columnImmobilier.setCellValueFactory(new PropertyValueFactory<>("bienName"));
        columnClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        columnProp.setCellValueFactory(new PropertyValueFactory<>("propName"));

        ObservableList<Contrat> contrats = FXCollections.observableArrayList(contratService.getAllContrats());
        tableView.setItems(contrats);
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

            Stage oldStage = (Stage) paneContrat.getScene().getWindow();
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
    public void createContract(ActionEvent event){
        try
        {
                double montant = Double.parseDouble(tfMontant.getText());
                TypeContrat tc = TypeContrat.valueOf(cbType.getSelectionModel().getSelectedItem().toString());
                Client client = clientService.getClientByName(cbClient.getSelectionModel().getSelectedItem().toString());
                Proprietaire proprietaire = proprietaireService.getProprietaireByName(cbProp.getSelectionModel().getSelectedItem().toString());
                BienImmobilier immobilier = bienService.getBienById(Integer.parseInt(cbImmo.getSelectionModel().getSelectedItem().toString()));


                Contrat contrat = Contrat.builder()
                        .montant(montant)
                        .proprietaire(proprietaire)
                        .bienImmobilier(immobilier)
                        .client(client)
                        .typeContrat(tc)
                        .associateName(cbClient.getSelectionModel().getSelectedItem().toString() +" & "+cbProp.getSelectionModel().getSelectedItem().toString())
                        .bienName(cbImmo.getSelectionModel().getSelectedItem().toString())
                        .clientName(cbClient.getSelectionModel().getSelectedItem().toString())
                        .propName(cbProp.getSelectionModel().getSelectedItem().toString())
                        .build();

                contratService.addContrat(contrat);
                showAlert(Alert.AlertType.INFORMATION, "Creation réussie","immo");
                initialize();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
