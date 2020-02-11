package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Logica.Services.HiloMensaje;
import com.martin.Models.EmailMensaje;
import com.martin.Models.EmailTreeItem;
import com.martin.Models.IniciarSesion;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import martin.Reloj;

import javax.mail.Folder;
import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private IniciarSesion inicio;
    @FXML
    private Button btnCuentas;
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
    @FXML
    private Reloj reloj;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VentanaLoginController loginController = (VentanaLoginController) cargarDialogo("VentanaLogin.fxml", 500, 400);
        loginController.getStage().setTitle("Iniciar Sesión");
        loginController.abrirDialogo(true);

        reloj.comenzar();

        try {
            EmailTreeItem item = Logica.getInstance().cargarCarpetas();

            treeView.setShowRoot(false);
            treeView.setRoot(item);
            treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {//nos suscribimos a cambios
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                   // Logica.getInstance().cargarMensajes(((EmailTreeItem)treeView.getSelectionModel().getSelectedItem()).getFolder());
                   // tvCorreos.setItems(Logica.getInstance().cargarMensajes(((EmailTreeItem)treeView.getSelectionModel().getSelectedItem()).getFolder()));
                    HiloMensaje servicio = new HiloMensaje(((EmailTreeItem)treeView.getSelectionModel().getSelectedItem()).getFolder());
                    servicio.start();
                    servicio.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent workerStateEvent) {
                            tvCorreos.setItems(servicio.getValue());
                        }
                    });
                }
            });

            tvCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailMensaje>() {
                @Override
                public void changed(ObservableValue<? extends EmailMensaje> observableValue, EmailMensaje oldValue, EmailMensaje newValue) {
                    webEngine = webVista.getEngine();

                    webEngine.loadContent(newValue.getContent());//gestionar cuando borras y trata de cargarlo, if(
                    tfRemitente.setText(newValue.getFrom());
                    tfAsunto.setText(newValue.getSubject());
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pantallaTareas(ActionEvent event){
        VentanaTareasController controller = (VentanaTareasController) cargarDialogo("VentanaTareas.fxml", 500, 500);
        controller.getStage().setTitle("Gestor de Tareas");
        controller.abrirDialogo(true);
    }

    @FXML
    public void examen(ActionEvent event){
        VentanaComboController controller = (VentanaComboController) cargarDialogo("VentanaCombo.fxml", 400, 400);
        controller.getStage().setTitle("pantalla combo");
        controller.abrirDialogo(true);
    }

    @FXML
    public void borrarEmail(ActionEvent event){
        EmailMensaje mensaje = tvCorreos.getSelectionModel().getSelectedItem();
        EmailTreeItem item = (EmailTreeItem) treeView.getSelectionModel().getSelectedItem();

        Logica.getInstance().borrarMensaje(mensaje, item);
        Folder f = ((EmailTreeItem) treeView.getSelectionModel().getSelectedItem()).getFolder();
        tvCorreos.setItems(Logica.getInstance().cargarMensajes(f));
    }
    @FXML
    public void gestionarCuentas(ActionEvent event){
        MisCuentasController cuentasController = (MisCuentasController) cargarDialogo("MisCuentas.fxml", 650, 540);
        cuentasController.getStage().setTitle("Iniciar Sesión");
        cuentasController.abrirDialogo(true);
        try {
            treeView.setRoot(Logica.getInstance().cargarCarpetas());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void escribir(ActionEvent event){
        EscribirCorreoController nuevoCorreo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 750, 600);
        nuevoCorreo.getStage().setTitle("Nuevo Correo");
        nuevoCorreo.abrirDialogo(true);
    }

    @FXML
    public void responder(ActionEvent event){
        String remitente;
        remitente = tvCorreos.getSelectionModel().getSelectedItem().getFrom();

        EscribirCorreoController nuevoCorreo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 750, 600);
        nuevoCorreo.setPara(remitente);
        nuevoCorreo.getStage().setTitle("Responder Correo");
        nuevoCorreo.abrirDialogo(true);
    }

    @FXML
    public void reenviar(ActionEvent event){
        String contenido = tvCorreos.getSelectionModel().getSelectedItem().getContent();
        EscribirCorreoController nuevoCorreo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 750, 600);
        nuevoCorreo.setContenido(contenido);
        nuevoCorreo.getStage().setTitle("Reenviar Correo");
        nuevoCorreo.abrirDialogo(true);
    }

}
