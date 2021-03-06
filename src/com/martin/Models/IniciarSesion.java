package com.martin.Models;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.Session;
import javax.mail.Store;

public class IniciarSesion {
    private TextField tfCorreo;
    private PasswordField pfContraseña;
    private String usuario;
    private String contraseña;
    private Store store;
    private Session session;


    public IniciarSesion(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }
    public boolean evaluarLogin(boolean respuesta){

        if(usuario.equalsIgnoreCase(usuario) &&
                contraseña.equals(contraseña)){
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

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public TextField getTfCorreo() {
        return tfCorreo;
    }
    public PasswordField getPfContraseña() {
        return pfContraseña;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
