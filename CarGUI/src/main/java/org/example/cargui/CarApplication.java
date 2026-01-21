package org.example.cargui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CarApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarApplication.class.getResource("car-simulator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Car Simulator");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
