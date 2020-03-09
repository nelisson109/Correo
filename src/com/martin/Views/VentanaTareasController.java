package com.martin.Views;

import com.martin.Logica.Logica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import martin.Accion;
import martin.Reloj;
import martin.Tarea;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    @FXML
    private Reloj r;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvTareas.setItems(Logica.getInstance().getListaTareas());
        r.comenzar();
        r.setFormatoHs(true);
        r.añadirAccion(new Accion() {
            @Override
            public void ejecuta(Tarea tarea) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TAREA");
                String fecha = tarea.getFecha();
                String hora = String.valueOf(tarea.getHoras());
                String min = String.valueOf(tarea.getMinutos());
                alert.setHeaderText(fecha + ", " + hora + ":" + min);
                alert.setContentText(tarea.getTexto());
                alert.show();
                r.borrarTarea(tarea);
                Logica.getInstance().getListaTareas().remove(tarea);
                tvTareas.refresh();
            }
        });
        tvTareas.refresh();
    }


    @FXML
    public void añadirTarea(ActionEvent event){
        //aquí sería conveniente meter una validación para que el usuario tenga que rellenar todos los campos
        int horas, minutos, segundos;
        Date fecha;
        LocalDate localDate;
        String texto;
        horas = Integer.parseInt(tfHoras.getText());
        minutos = Integer.parseInt(tfMinutos.getText());
        segundos = Integer.parseInt(tfSegundos.getText());
        texto = tfTexto.getText();
        localDate = dpFecha.getValue();
        fecha = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Tarea tarea = new Tarea(horas, minutos, segundos, fecha, texto);
        Logica.getInstance().añadirTarea(tarea);
        r.registrarTarea(tarea);//?¿
        tvTareas.refresh();
        tfHoras.setText("");
        tfMinutos.setText("");
        tfSegundos.setText("");
        tfTexto.setText("");

    }

    @FXML
    public void borrarTarea(ActionEvent event){
        Logica.getInstance().borrarTarea(tvTareas.getSelectionModel().getSelectedItem());
        r.borrarTarea(tvTareas.getSelectionModel().getSelectedItem());
        tvTareas.refresh();

    }
}
