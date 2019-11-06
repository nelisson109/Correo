package com.martin.Views;

import com.martin.Models.IniciarSesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaLoginController implements Initializable {

    @FXML
    private TextField tfCorreo;

    @FXML
    private PasswordField pfContraseña;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCrearCuenta;

    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IniciarSesion inicio = new IniciarSesion(tfCorreo, pfContraseña);
        boolean respuesta = inicio.evaluarLogin();

        if(respuesta==true){

        }
    }
}
