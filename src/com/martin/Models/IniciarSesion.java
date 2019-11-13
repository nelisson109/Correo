package com.martin.Models;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class IniciarSesion {
    private TextField tfCorreo;
    private PasswordField pfContraseña;

    public IniciarSesion(TextField tfCorreo, PasswordField pfContraseña) {
        this.tfCorreo = tfCorreo;
        this.pfContraseña = pfContraseña;
    }
    public boolean evaluarLogin(boolean respuesta){

        if(tfCorreo.getText().equalsIgnoreCase("gavadianmartin@gmail.com") &&
                pfContraseña.getText().equals("helipi67")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Login correcto");
            alert.setContentText("El usuario y la contraseña introducidos han sido correctos");
            alert.showAndWait();
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login incorrecto");
            alert.setContentText("El usuario y/o la contraseña introducidos no son correctos");
            alert.showAndWait();
            tfCorreo.setText("");
            pfContraseña.setText("");
            return false;
        }
    }

    public TextField getTfCorreo() {
        return tfCorreo;
    }
    public PasswordField getPfContraseña() {
        return pfContraseña;
    }
}
