package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Models.IniciarSesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.ResourceBundle;

public class EscribirCorreoController extends BaseController implements Initializable {
    @FXML
    private TextField tfPara;

    @FXML
    private TextField tfAsunto;

    @FXML
    private HTMLEditor cajaHtml;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnAdjuntar;
    @FXML
    private ComboBox<String> cbMisCuentas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i< Logica.getInstance().getListaCuentas().size(); i++){
            cbMisCuentas.getItems().add(Logica.getInstance().getListaCuentas().get(i).getUsuario());
        }

    }
    @FXML
    public void enviar(ActionEvent event){
        IniciarSesion iniciarSesion = null;
        String usuario = cbMisCuentas.getSelectionModel().getSelectedItem();
       // if(Logica.getInstance().conexion(iniciarSesion))
            Logica.getInstance().escribirCorreo(usuario, tfPara.getText(), tfAsunto.getText(), cajaHtml);
        getStage().close();


    }

    public void setPara(String para){
        tfPara.setText(para);
    }

    public void setAsunto(String asunto){
        tfAsunto.setText(asunto);
    }

    public void setContenido(String contenido){
        cajaHtml.setHtmlText(contenido);
    }

    @FXML
    public void cancelar(ActionEvent event){
        //cerrar ventana
        getStage().close();
    }
}
