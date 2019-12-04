package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.IniciarSesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

public class MisCuentasController extends BaseController implements Initializable {
    @FXML
    private TableView<IniciarSesion> tvCuentas;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnModificar;

    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField pfContraseña;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvCuentas.setItems(Logica.getInstance().getListaCuentas());
    }

    @FXML
    public void añadirCuenta(ActionEvent event){
        String usuario, contraseña;

        boolean respuesta;
       // IniciarSesion cuenta = new IniciarSesion("eduardocapinjavafx@gmail.com", "eduardojavafx");

        VentanaLoginController loginController = (VentanaLoginController) cargarDialogo("VentanaLogin.fxml", 500, 400);
        loginController.getStage().setTitle("Iniciar Sesión");
        loginController.abrirDialogo(true);
        usuario = tfCorreo.getText();
        contraseña = pfContraseña.getText();
        IniciarSesion cuenta = new IniciarSesion(usuario, contraseña);

        respuesta = Logica.getInstance().conexion(cuenta);
        cuenta.evaluarLogin(respuesta);
        tvCuentas.setItems(Logica.getInstance().getListaCuentas());


    }
    @FXML
    public void borrarCuenta(){
        Logica.getInstance().borrarCuenta(tvCuentas.getSelectionModel().getSelectedItem());
        /*
        * if(folder!=papelera)-->copiarEmail(folder, papelera);
        * else
        * mail.flag(DELETE);
        * folder.close();*/
    }
}
