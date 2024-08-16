package org.capacity.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.capacity.service.DatabaseManager;
import org.capacity.utilities.Constants;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.getInstance();
        Parent root = FXMLLoader.load(this.getClass().getResource(Constants.MAIN));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(Constants.APPLICATION_NAME);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}