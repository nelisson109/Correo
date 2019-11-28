package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.IniciarSesion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaLoginController extends BaseController implements Initializable {

    @FXML
    private TextField tfCorreo;

    @FXML
    private PasswordField pfContraseña;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCrearCuenta;//dejar de momento, aqui seria un enlace para llevar al usuario a un formulario de alta en gmail

    private String usuario, contraseña;
    private boolean respuesta;

    public TextField getTfCorreo(){//cambiar metodo a string
        return tfCorreo;
    }
    public PasswordField getPfContraseña(){//cambiar metodo a string
        return pfContraseña;
    }
    public String getUsuario(){
        return usuario;
    }
    public String getContraseña(){
        return contraseña;
    }

    @FXML
    public void entrar(ActionEvent event) throws MessagingException {
        usuario = tfCorreo.getText();
        contraseña = pfContraseña.getText();
        IniciarSesion inicioCuenta = new IniciarSesion(usuario, contraseña);

        respuesta = Logica.getInstance().conexion(inicioCuenta);
        inicioCuenta.evaluarLogin(respuesta);
        if (respuesta==true)
            Logica.getInstance().cargarCuentas(inicioCuenta);


        Logica.getInstance().actualizarTree();//cooper

        getStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfCorreo.setText("martinlg36dam@gmail.com");
        pfContraseña.setText("helipi67");
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(tfCorreo, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        validationSupport.registerValidator(pfContraseña, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                btnLogin.setDisable(newValue);
            }
        });
    }
}
