module Unidad2javaFXejemplo {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.mail;
    exports com.martin;
    exports com.martin.Logica;
    exports com.martin.Models;
    exports com.martin.Views;
    exports com.martin.Utils;

    opens com.martin.views to javafx.fxml;
}