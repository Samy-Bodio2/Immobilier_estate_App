package com.example.immobilier.Controller;

import com.example.immobilier.Entity.BienImmobilier;
import com.example.immobilier.Entity.Contrat;
import com.example.immobilier.Entity.ExtraType.CategorieBien;
import com.example.immobilier.Entity.ExtraType.Statut;
import com.example.immobilier.Entity.ExtraType.TypeContrat;
import com.example.immobilier.Entity.ExtraType.TypeTransaction;
import com.example.immobilier.Entity.Proprietaire;
import com.example.immobilier.Service.BienService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ImmoController {
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private BienService bienService;
    @Autowired
    private ProprietaireService proprietaireService;

    @FXML
    private ComboBox cbType;
    @FXML
    private TextField tfSurface;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfPrix;
    @FXML
    private ComboBox cbStatut;
    @FXML
    private ComboBox cbProp;
    @FXML
    private Button imgBtn;
    @FXML
    private Label lblImg;

    @FXML
    private TableView<BienImmobilier> tableView;

    @FXML
    private TableColumn<BienImmobilier,Integer> columnId;
    @FXML
    private TableColumn<BienImmobilier,CategorieBien> columnType;
    @FXML
    private TableColumn<BienImmobilier,String> columnAdresse;
    @FXML
    private TableColumn<BienImmobilier,Double> columnSurface;
    @FXML
    private TableColumn<BienImmobilier,Integer> columnPrix;
    @FXML
    private TableColumn<BienImmobilier,String> columnImage;
    @FXML
    private TableColumn<BienImmobilier,Statut> columnStatut;
    @FXML
    private TableColumn<BienImmobilier,String> columnNameProp;
    @FXML
    private Pane paneBiens;

    public ImmoController() {
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
        loadImmosView();
        loadImmoTypes();
        loadImmos();
    }

    private void loadImmoTypes(){
        cbType.getItems().addAll(CategorieBien.values());
        cbStatut.getItems().addAll(Statut.values());
    }

    private void loadImmos(){
       List<String> nom = proprietaireService.getAllProprietaires().stream().map(Proprietaire::getNom).toList();
        cbProp.getItems().addAll(nom);
    }

    private void loadImmosView(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("idBien"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        columnSurface.setCellValueFactory(new PropertyValueFactory<>("surface"));
        columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        columnImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        columnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        columnNameProp.setCellValueFactory(new PropertyValueFactory<>("nameProp"));

        columnImage.setCellFactory(column -> {
            return new TableCell<>() {
                private final ImageView imageView = new ImageView();



                @Override
                protected void updateItem(String image, boolean empty) {
                    super.updateItem(image, empty);
                    if (empty || image == null) {
                        setGraphic(null);
                    } else {
                        Image imageV = new Image(image, 50, 50, true, true); // Taille des images
                        imageView.setImage(imageV);
                        imageView.setPickOnBounds(true);
                        setOnMouseClicked(event -> {
                            if(event.getClickCount() == 1){
                                showLargeImage(image);
                            }
                        });
                        setGraphic(imageView);
                    }
                }
            };
        });

        ObservableList<BienImmobilier> biens = FXCollections.observableArrayList(bienService.getAllBiens());
        tableView.setItems(biens);
    }



    private void showLargeImage(String imagePath) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Modal

        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(500); // Largeur de l'image
        imageView.setFitHeight(500); // Hauteur de l'image

        System.out.println("false    flase");

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 600, 600); // Taille de la fenêtre
        stage.setScene(scene);
        stage.setTitle("Image en grand");
        stage.show();
    }

    private void navigateTo(String str) {
        try {
            FXMLLoader fxmlLoader = springFXMLLoader.load("/com/example/immobilier/view/"+str+".fxml");
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Page "+str);

            stage.setScene(scene);
            stage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("Clic détecté globalement : " + event.getSource());
            });
            stage.show();

            Stage oldStage = (Stage) paneBiens.getScene().getWindow();
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
    private void createImmo(ActionEvent actionEvent){
        try{
                    double Surface = Double.parseDouble(tfSurface.getText()) ;
                    String Adresse = tfAdresse.getText();
                    int Prix = Integer.parseInt(tfPrix.getText()) ;
                    String img = lblImg.getText();
                    Proprietaire proprietaire = proprietaireService.getProprietaireByName(cbProp.getSelectionModel().getSelectedItem().toString());

                    BienImmobilier bienImmobilier = BienImmobilier.builder()
                            .type(CategorieBien.valueOf(cbType.getSelectionModel().getSelectedItem().toString()))
                            .surface(Surface)
                            .adresse(Adresse)
                            .prix(Prix)
                            .image(img)
                            .nameProp(cbProp.getSelectionModel().getSelectedItem().toString())
                            .proprietaire(proprietaire)
                            .statut(Statut.valueOf(cbStatut.getSelectionModel().getSelectedItem().toString()))
                            .build();
                    bienService.addBienImmobilier(bienImmobilier);
            showAlert(Alert.AlertType.INFORMATION, "Creation réussie","immo");
            initialize();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

//    public void addImmoBienStatus(BienImmobilier immo,String statut){
//        if(Objects.equals(statut,"Disponible")){
//            BienImmobilier bienImmobilier = BienImmobilier.builder()
//                    .type(immo.getType())
//                    .surface(immo.getSurface())
//                    .adresse(immo.getAdresse())
//                    .prix(immo.getPrix())
//                    .statut(Statut.Disponible)
//                    .build();
//            bienService.addBienImmobilier(bienImmobilier);
//            showAlert(Alert.AlertType.INFORMATION, "Creation réussie","immo");
//
//        }else if(Objects.equals(statut,"Loue")){
//            BienImmobilier bienImmobilier = BienImmobilier.builder()
//                    .type(immo.getType())
//                    .surface(immo.getSurface())
//                    .adresse(immo.getAdresse())
//                    .prix(immo.getPrix())
//                    .statut(Statut.Loue)
//                    .build();
//            bienService.addBienImmobilier(bienImmobilier);
//            showAlert(Alert.AlertType.INFORMATION, "Creation réussie","immo");
//        }
//        else if(Objects.equals(statut,"Vendu")){
//            BienImmobilier bienImmobilier = BienImmobilier.builder()
//                    .type(immo.getType())
//                    .surface(immo.getSurface())
//                    .adresse(immo.getAdresse())
//                    .prix(immo.getPrix())
//                    .statut(Statut.Vendu)
//                    .build();
//            bienService.addBienImmobilier(bienImmobilier);
//            showAlert(Alert.AlertType.INFORMATION, "Creation réussie","immo");
//        }else{
//
//        }
//    }

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
