package com.martin;

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
       // Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainWindow.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 675, 575));
        stage.show();
       /* TreeView<File> treeView = new TreeView<File>();
        TreeItem<File> bandeja = new TreeItem<File>();
        treeView.setShowRoot(false);
        treeView.setRoot(bandeja);
        File [] roots = File.listRoots();
        for(File disk : roots){
            bandeja.getChildren().add(createNode(disk));
        }*/



    }
/*    private TreeItem<File> createNode(final File f){
        return new TreeItem<File>(f);
    }
    public ObservableList<TreeItem<File>> buildChildren(TreeItem <File> treeItem){//Este metodo genera un treeItem por cada archivo de la carpeta
        File f = treeItem.getValue();//creamos un objeto carpeta que ira cogiendo los valores treeItem
        if(f != null && f.isDirectory()){
            File [] files = f.listFiles();//Los valores que va tomando f, los guardamos en una lista. Una lista de carpetas
            if (files!= null){
                ObservableList<TreeItem<File>> hijos = FXCollections.observableArrayList();
                for (File carpetaHija : files){
                    hijos.add(createNode(carpetaHija));//añadimos los treeItems a la lista de carpetas
                }
                return hijos;//devolvemos la lista observable de carpetas(File)
            }
        }
        return FXCollections.emptyObservableList();
    }*/
    public static void main(String args[]){
        launch();
    }
}
