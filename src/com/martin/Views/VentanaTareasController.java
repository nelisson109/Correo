package com.martin.Views;

import com.martin.Logica.Logica;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import martin.Tarea;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaTareasController extends BaseController implements Initializable {
    @FXML
    private TableView<Tarea> tvTareas;

    @FXML
    private Button btnAñadir;

    @FXML
    private Button btnEliminar;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField tfHoras;

    @FXML
    private TextField tfMinutos;

    @FXML
    private TextField tfSegundos;

    @FXML
    private TextField tfTexto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvTareas.setItems(Logica.getInstance().getListaTareas());
    }

    @FXML
    public void añadirTarea(){
        //aquí sería conveniente meter una validación para que el usuario tenga que rellenar todos los campos

    }
}
