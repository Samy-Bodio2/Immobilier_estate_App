package com.example.immobilier.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AuthController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            // Affiche un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Bienvenue, " + username);

            // Redirige vers la page client
            redirectToClientPage();

            // Ferme la fenêtre actuelle (login)
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        } else {
            // Affiche un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Erreur", "Nom d'utilisateur ou mot de passe incorrect");
        }
    }

    // Méthode pour valider les informations d'identification
    private boolean isValidLogin(String username, String password) {
        // Exemple simple : vérification des valeurs statiques (admin, admin)
        return "admin".equals(username) && "admin".equals(password);
    }

    // Méthode pour rediriger l'utilisateur vers la page client
    private void redirectToClientPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AuthController.class.getResource("/com/example/immobilier/view/Client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Page Client");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger la page client");
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
