package com.martin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/mainActivity.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 675, 575));
        stage.show();

    }
    public static void main(String args[]){
        launch();
    }
}
