package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.EmailMensaje;
import com.martin.Models.EmailTreeItem;
import com.martin.Models.IniciarSesion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class MainWindowController extends BaseController implements Initializable {

    private IniciarSesion inicio;
    @FXML
    private Button btnEscribir;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnResponder;
    @FXML
    private Button btnReenviar;
    @FXML
    private Menu barAcciones;
    @FXML
    TreeView treeView;
    @FXML
    private MenuItem itemNuevo;//seria btnEscribir
    @FXML
    private MenuItem itemBorrar;
    @FXML
    private MenuItem itemResponder;
    @FXML
    private MenuItem itemReenviar;
    @FXML
    private WebView webVista;
    private WebEngine webEngine;
    @FXML
    private TableView<EmailMensaje> tvCorreos;
    @FXML
 /*   public void AltaCuenta(ActionEvent event){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaLogin.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Inicio Sesión");
            stage.setScene(new Scene(root, 500, 575));
            stage.showAndWait();
            comprobarUsuario();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void comprobarUsuario(){//mirar como cuadrar esto

        boolean respuesta = Logica.getInstance().cargarMensajes(0);

        if(respuesta==true){
           // tvCuentas.setItems();//cargamos los dos tablesViews, cuentas y correos
            tvCorreos.setItems(Logica.getInstance().getListaMensajes());
        }
        else{
            //inicio.evaluarLogin();
        }
    }*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VentanaLoginController loginController = (VentanaLoginController) cargarDialogo("VentanaLogin.fxml", 500, 400);
        loginController.abrirDialogo(true);
        tvCorreos.setItems(Logica.getInstance().getListaMensajes());
       // tvCorreos.getSelectionModel().select(0);//nos muestra el primero de la lista para empezar. probando
        try {
            EmailTreeItem item = Logica.getInstance().cargarCarpetas();
            treeView.setShowRoot(false);
            treeView.setRoot(item);
            treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {//nos suscribimos a cambios
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object nexValue) {
                    Logica.getInstance().cargarMensajes((Folder) treeView.getSelectionModel().getSelectedItem());
                    tvCorreos.setItems(Logica.getInstance().getListaMensajes());
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
       // EmailMensaje mensaje = tvCorreos.getSelectionModel().getSelectedItem();
    }
}
