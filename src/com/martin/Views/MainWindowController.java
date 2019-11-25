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
    private TextField tfRemitente, tfAsunto;
    @FXML
    private TableView<EmailMensaje> tvCorreos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VentanaLoginController loginController = (VentanaLoginController) cargarDialogo("VentanaLogin.fxml", 500, 400);
        loginController.getStage().setTitle("Iniciar Sesión");
        loginController.abrirDialogo(true);

        Logica.getInstance().cargarMensajes();//cargo la lista de mensajes.
        tvCorreos.setItems(Logica.getInstance().getListaMensajes());//muestro la lista de mensajes en el tableview. Hay que quitarla para q muestre lo de cada carpeta

       // tvCorreos.getSelectionModel().select(0);//nos muestra el primero de la lista para empezar. probando
        try {
            EmailTreeItem item = Logica.getInstance().cargarCarpetas();//hay que cambiar el metodo a llenandoCarpetas

            Logica.getInstance().cargarCuentas(inicio);

            treeView.setShowRoot(false);
            treeView.setRoot(item);
            treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {//nos suscribimos a cambios
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    tvCorreos.setItems(Logica.getInstance().getListaMensajes());

                }
            });

            treeView.getSelectionModel().select(1);

            tvCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailMensaje>() {
                @Override
                public void changed(ObservableValue<? extends EmailMensaje> observableValue, EmailMensaje oldValue, EmailMensaje newValue) {
                    webEngine = webVista.getEngine();
                    webEngine.loadContent(newValue.getContent());
                    tfRemitente.setText(newValue.getFrom());
                    tfAsunto.setText(newValue.getSubject());
                }
            });


        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
