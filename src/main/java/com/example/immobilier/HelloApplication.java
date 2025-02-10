package com.example.immobilier;

import com.example.immobilier.Controller.ClientController;
import com.example.immobilier.Service.ClientService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class HelloApplication extends Application {

    private static ApplicationContext springContext;

    @Override
    public void init() throws Exception {
        // Initialiser le contexte Spring
        springContext = new SpringApplicationBuilder(SpringStarter.class)
                .run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/Client.fxml"));
       fxmlLoader.setControllerFactory(springContext::getBean); // Utilise Spring pour instancier les contrôleurs


        // Créer la scène JavaFX
        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
