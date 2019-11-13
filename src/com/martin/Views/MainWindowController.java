package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.Cuenta;
import com.martin.Models.EmailMensaje;
import com.martin.Models.IniciarSesion;
import com.martin.Models.MensajeCorreo;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

public class MainWindowController extends BaseController implements Initializable {
    private Cuenta cuenta;
    private IniciarSesion inicio;
    @FXML
    private TableView<Cuenta> tvCuentas;
    @FXML
    private TableColumn<Cuenta, IniciarSesion> tcCuenta;
    @FXML
    private TableColumn<Cuenta, String> tcTitular;
    @FXML
    private MenuBar menuCuenta;
    @FXML
    private Menu menuCuentas;
    @FXML
    private MenuItem itemAlta;
    @FXML
    private MenuItem itemBorrarCuenta;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBorrar;
    @FXML
    private Menu barAcciones;
    @FXML
    private MenuItem itemNuevo;
    @FXML
    private MenuItem itemBorrar;
    @FXML
    private MenuItem itemResponder;
    @FXML
    private MenuItem itemReenviar;
    @FXML
    private WebView webVista;
    @FXML
    private WebEngine webEngine;
    @FXML
    private TableView<EmailMensaje> tvCorreos;//faltan los tablecolum
    @FXML
    public void AltaCuenta(ActionEvent event){
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
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VentanaLoginController loginController = (VentanaLoginController) cargarDialogo("VentanaDialogo.fxml", 500, 400);
        loginController.getStage().setResizable(false);//mirar
        loginController.abrirDialogo(true);
        tvCorreos.setItems(Logica.getInstance().getListaMensajes());
        tvCorreos.getSelectionModel().select(0);//indice
        webEngine = webVista.getEngine();

        webEngine.loadContent((String) tvCorreos.getSelectionModel().getSelectedItem().getContent());
        //Logica.getInstance().cargarCuentas(loginController.getTfCorreo(), loginController.getPfContraseña());


    }
    @FXML
    public void elegirCorreo(ActionEvent event){
        webEngine = webVista.getEngine();
        webEngine.loadContent((String) tvCorreos.getSelectionModel().getSelectedItem().getContent());

    }
}
