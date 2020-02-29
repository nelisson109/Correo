package com.martin.Views;

import com.martin.Logica.Logica;
import com.martin.Logica.Services.HiloMensaje;
import com.martin.Models.*;
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
import javafx.stage.Stage;
import martin.Reloj;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jsoup.Jsoup;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class MainWindowController extends BaseController implements Initializable {

    private Stage stage = new Stage();

    private IniciarSesion inicio;
    @FXML
    private Button help;
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
                    HiloMensaje servicio = new HiloMensaje(((EmailTreeItem) treeView.getSelectionModel().getSelectedItem()).getFolder());
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
    public void imprimirEmail(ActionEvent event) {//informe para un email
        List<Email> listaEmail = new ArrayList<>();
        EmailMensaje correo = tvCorreos.getSelectionModel().getSelectedItem();
        String texto = Jsoup.parse(correo.getContent()).text();
        Email email = new Email(correo.getFrom(), correo.getSubject(), texto, correo.getFecha());
        listaEmail.add(email);

        if (correo != null) {
            try {
                JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(listaEmail);
                Map<String, Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/com/martin/Informes/informeEmail.jasper"), parametros, jrds);
                JasperExportManager.exportReportToPdfFile(print, "informesCorreo\\informeEmail.pdf");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Informe generado");
                alert.setContentText("El informe ha sido generado correctamente");
                alert.showAndWait();
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informe NO generado");
            alert.setContentText("CUIDADO!! El informe no ha podido ser generado");
            alert.showAndWait();
        }
    }

    @FXML
    public void imprimirCarpeta(ActionEvent event) {//informe para los correos de una carpeta
        EmailTreeItem item = (EmailTreeItem) treeView.getSelectionModel().getSelectedItem();
        Folder folder = item.getFolder();
        //Folder folder = (Folder) treeView.getSelectionModel().getSelectedItem();
        List<EmailMensaje> lista;
        lista = tvCorreos.getItems();
        List<EmailsCarpeta> listaEmail = new ArrayList<>();

        try {
            for (EmailMensaje e : lista) {
                String remitente = e.getFrom();
                String destinatario = e.getTo();
                String asunto = e.getSubject();
                Date date = e.getFecha();
                listaEmail.add(new EmailsCarpeta(remitente, destinatario, asunto, date, folder.toString()));
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(listaEmail);
            Map<String, Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/com/martin/Informes/informeEmailsCarpeta.jasper"), parametros, jrds);
            JasperExportManager.exportReportToPdfFile(print, "informesCorreo\\informeEmailsCarpeta.pdf");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void imprimirPorCarpetas(ActionEvent event) {
        Store store;
        store = Logica.getInstance().getListaCuentas().get(0).getStore();
        try {
            Folder[] vectorCarpetas = store.getDefaultFolder().list();
            for (Folder folder : vectorCarpetas) {
                cargarCarpetas(folder);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(listaEmails);
            Map<String, Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/com/martin/Informes/informeCorreosCuenta.jasper"), parametros, jrds);
            JasperExportManager.exportReportToPdfFile(print, "informesCorreo\\informeCorreosCuenta.pdf");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    private List<EmailMensaje> listaEmailMensaje = new ArrayList<>();
    private List<EmailsCarpeta> listaEmails = new ArrayList<>();
    public void cargarCarpetas(Folder folder) {
        try {
            if (!folder.isOpen() && folder.getType() == 3) {
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                EmailMensaje correo;
                for (int i = 0; i < messages.length; i++) {
                    correo = new EmailMensaje(messages[i]);
                    listaEmailMensaje.add(correo);
                }
                for (EmailMensaje e : listaEmailMensaje) {
                    listaEmails.add(new EmailsCarpeta(e.getFrom(), e.getTo(), e.getSubject(), e.getFecha(), folder.toString()));
                }
            } else {
                for (Folder f : folder.list()) {
                    cargarCarpetas(folder);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void pantallaTareas(ActionEvent event) {
        VentanaTareasController controller = (VentanaTareasController) cargarDialogo("VentanaTareas.fxml", 800, 500);
        controller.getStage().setTitle("Gestor de Tareas");
        controller.abrirDialogo(true);
    }

    @FXML
    public void examen(ActionEvent event) {
        VentanaComboController controller = (VentanaComboController) cargarDialogo("VentanaCombo.fxml", 400, 400);
        controller.getStage().setTitle("pantalla combo");
        controller.abrirDialogo(true);
    }

    @FXML
    public void borrarEmail(ActionEvent event) {
        EmailMensaje mensaje = tvCorreos.getSelectionModel().getSelectedItem();
        EmailTreeItem item = (EmailTreeItem) treeView.getSelectionModel().getSelectedItem();

        Logica.getInstance().borrarMensaje(mensaje, item);
        Folder f = ((EmailTreeItem) treeView.getSelectionModel().getSelectedItem()).getFolder();
        tvCorreos.setItems(Logica.getInstance().cargarMensajes(f));
    }

    @FXML
    public void gestionarCuentas(ActionEvent event) {
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
    public void escribir(ActionEvent event) {
        EscribirCorreoController nuevoCorreo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 750, 600);
        nuevoCorreo.getStage().setTitle("Nuevo Correo");
        nuevoCorreo.abrirDialogo(true);
    }

    @FXML
    public void responder(ActionEvent event) {
        String remitente;
        remitente = tvCorreos.getSelectionModel().getSelectedItem().getFrom();
        EscribirCorreoController nuevoCorreo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 750, 600);
        nuevoCorreo.setPara(remitente);
        nuevoCorreo.getStage().setTitle("Responder Correo");
        nuevoCorreo.abrirDialogo(true);
    }

    @FXML
    public void reenviar(ActionEvent event) {
        String contenido = tvCorreos.getSelectionModel().getSelectedItem().getContent();
        EscribirCorreoController nuevoCorreo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 750, 600);
        nuevoCorreo.setContenido(contenido);
        nuevoCorreo.getStage().setTitle("Reenviar Correo");
        nuevoCorreo.abrirDialogo(true);
    }

    public void ayuda() throws Exception {
        AyudaController ayudaController = new AyudaController();
        ayudaController.start(stage);
    }

}
