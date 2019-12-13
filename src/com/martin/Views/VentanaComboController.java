package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.EmailMensaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaComboController extends BaseController implements Initializable {

    @FXML
    private ComboBox<EmailMensaje> cbMensajes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbMensajes.setItems(Logica.getInstance().getListaMensajes());

    }

    @FXML
    public void mostrarAlert(ActionEvent event){
        EmailMensaje mensaje = cbMensajes.getValue();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Login correcto");
        alert.setContentText("Asunto: " + mensaje.getSubject());
        alert.showAndWait();
    }
}
