package com.martin;

import com.martin.Logica.Logica;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.File;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        File fichero = new File("ficheroCuentas.dat");
        if(fichero.exists()){
            Logica.getInstance().leerObjetos(fichero);
        }
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainWindow.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 1100, 600));
        stage.show();
    }
    public static void main(String args[]){
        launch();
    }
}
