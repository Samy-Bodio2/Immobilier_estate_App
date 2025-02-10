package com.example.immobilier.Controller;

import com.example.immobilier.Entity.BienImmobilier;
import com.example.immobilier.Entity.Contrat;
import com.example.immobilier.Entity.ExtraType.TypeContrat;
import com.example.immobilier.Entity.ExtraType.TypeTransaction;
import com.example.immobilier.Entity.Transaction;
import com.example.immobilier.Service.ContratService;
import com.example.immobilier.Service.TransactionService;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ContratService contratService;

    @FXML
    private ComboBox cbContrat;

    @FXML
    private Pane paneTransaction;

    @FXML
    private TextField tfAmount;
    @FXML
    private DatePicker dtDate;
    @FXML
    private ComboBox cbType ;

    private TypeTransaction transaction;

    @FXML
    private TableView<Transaction> tableView;
    @FXML
    private TableColumn<Transaction,Integer> columnId;
    @FXML
    private TableColumn<Transaction,String> columnDate;
    @FXML
    private TableColumn<Transaction,Double> columnMontant;
    @FXML
    private TableColumn<Transaction,TypeTransaction> columnType;
    @FXML
    private TableColumn<Contrat,String> columnAssociate;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    public TransactionController() {
    }

    public void initialize() {
        // Initialisation des données
        loadComboBoxData();
        loadContratList();
        loadTransactions();
    }

    private void loadTransactions() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idTransaction"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateTransaction"));
        columnMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("typeTransaction"));
        columnAssociate.setCellValueFactory(new PropertyValueFactory<>("associateTransaction"));


        ObservableList<Transaction> transactions = FXCollections.observableArrayList(transactionService.getAllTransactions());
        tableView.setItems(transactions);
    }

    private void loadContratList(){
         List<Integer> idsC =  contratService.getAllContrats().stream().map(Contrat::getIdContrat).collect(Collectors.toList());
         List<String> associates = new ArrayList<>();
         for(int id : idsC){
             associates.add(id +" "+transactionService.getAssociateNameByContractId(id));
         }
        cbContrat.getItems().addAll(associates);
    }

    private void loadComboBoxData() {
        // Ajouter les éléments à la ComboBox
        cbType.getItems().addAll(TypeTransaction.values());
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

    private void navigateTo(String str) {
        try
        {
            FXMLLoader fxmlLoader = springFXMLLoader.load("/com/example/immobilier/view/"+str+".fxml");
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Page "+str);
            stage.setScene(scene);
            stage.show();

            Stage oldStage = (Stage) paneTransaction.getScene().getWindow();
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

    private String getIntValue(String str){
        StringBuilder Nstr= new StringBuilder();
        for (int i = 0; i <= str.length(); i++) {
            if (!(str.charAt(i) ==' ')){
                Nstr.append(str.charAt(i));
            }
            else{
                break;
            }
        }
        return Nstr.toString();
    }
    
    @FXML
    private void createTransaction(ActionEvent event) {
        try{
            if(Objects.equals(cbType.getSelectionModel().getSelectedItem().toString(), "Paiement")){

                String dateStr = dtDate.getValue().toString();
                double amount = Double.parseDouble(tfAmount.getText()) ;
                Contrat contrat = contratService.getContratById(Integer.parseInt(getIntValue(cbContrat.getSelectionModel().getSelectedItem().toString())));
                String associateName = transactionService.getAssociateNameByContractId(contrat.getIdContrat());

                Transaction transaction = Transaction.builder()
                        .dateTransaction(dateStr)
                        .typeTransaction(TypeTransaction.Paiement)
                        .montant(amount)
                        .associateTransaction(associateName)
                        .contratAssocie(contrat)
                        .build();

                transactionService.addTransaction(transaction);
                showAlert(Alert.AlertType.INFORMATION, "Creation réussie","Transaction");
                initialize();

            }else if(Objects.equals(cbType.getSelectionModel().getSelectedItem().toString(), "Depot")){
                String dateStr = dtDate.getValue().toString();
                double amount = Double.parseDouble(tfAmount.getText()) ;

                Transaction transaction = Transaction.builder()
                        .dateTransaction(dateStr)
                        .typeTransaction(TypeTransaction.Depot)
                        .montant(amount)
                        .build();

                transactionService.addTransaction(transaction);
                showAlert(Alert.AlertType.INFORMATION, "Creation réussie","Transaction");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
