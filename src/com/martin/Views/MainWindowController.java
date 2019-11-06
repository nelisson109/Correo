package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.Cuenta;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {
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
    private TableView<MensajeCorreo> tvCorreos;//faltan los tablecolum
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
            //poner metodo comprobar
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void comprobarUsuario(){

        boolean respuesta = inicio.evaluarLogin();

        if(respuesta==true){
           // tvCuentas.setItems();//cargamos los dos tablesViews, cuentas y correos

            tvCorreos.setItems(Logica.getInstance().getMensaje());

        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
