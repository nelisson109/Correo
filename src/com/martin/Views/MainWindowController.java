package com.martin.Views;

import com.martin.Models.IniciarSesion;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {
    @FXML
    private TableView<?> tvCuentas;
    @FXML
    private TableColumn<?, ?> tcCuenta;
    @FXML
    private TableColumn<?, ?> tcTitular;
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
    private TableView<?> tvCorreos;
    @FXML
    public void comprobarUsuario(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaLogin.fxml"));
            Parent root = fxmlLoader.load();
            VentanaLoginController loginController = fxmlLoader.getController();
            IniciarSesion inicio =
            /* Partido partido = tvpartidos.getSelectionModel().getSelectedItem();
            controller.setPartidoModificar(partido);*/
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Inicio Sesión");
            stage.setScene(new Scene(root, 500, 575));
            stage.showAndWait();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
