package com.example.immobilier.Controller;

import com.example.immobilier.Entity.Agent;
import com.example.immobilier.Entity.Client;
import com.example.immobilier.Service.AgentService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;

@Controller
@NoArgsConstructor
public class AgentController {

    @Autowired
    private  AgentService agentService;
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @FXML
    private TableColumn<Agent,Integer> columnId;
    @FXML
    private TableColumn<Agent,String> columnFirst;
    @FXML
    private TableColumn<Agent,String> columnLast;
    @FXML
    private TableColumn<Agent, Integer> columnPhone;
    @FXML
    private TableColumn<Agent,String> columnImage;
    @FXML
    private TableView<Agent> tableView;

    @FXML
    private Pane paneAgent;
    @FXML
    private Button imgBtn;
    @FXML
    private Label lblImg;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfLastName;


    public void initialize(){
        loadTableView();
    }

    private void loadTableView(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        columnFirst.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        columnLast.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        columnImage.setCellFactory(
                column -> {
                    return new TableCell<>(){
                        private final ImageView imageView = new ImageView();

                        protected void updateItem()
                    }
                }
        );
        ObservableList<Agent> agents = FXCollections.observableArrayList(agentService.getAllAgents());
        tableView.setItems(agents);
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
        try {
            FXMLLoader fxmlLoader = springFXMLLoader.load("/com/example/immobilier/view/"+str+".fxml");
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Page "+str);
            stage.setScene(scene);
            stage.show();

            Stage oldStage = (Stage) paneAgent.getScene().getWindow();
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
    public void createAgent(ActionEvent event){
        try{
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String phone = tfPhone.getText();
            String img = lblImg.getText();

            Agent agent = Agent.builder()
                    .nom(firstName)
                    .prenom(lastName)
                    .telephone(phone)
                    .image(img)
                    .build();
            agentService.addAgent(agent);
            showAlert(Alert.AlertType.INFORMATION, "Creation réussie",firstName);
            initialize();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void selectImage(ActionEvent event) {
        // Créer le FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionnez une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Ouvrir la boîte de dialogue
        File selectedFile = fileChooser.showOpenDialog(imgBtn.getScene().getWindow());
//        selectedFile.toURI().toString()
        lblImg.setText(selectedFile.toURI().toString());
        // Charger et afficher l'image si un fichier est sélectionné
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
        }
    }
}

