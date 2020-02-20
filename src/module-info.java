module Unidad2javaFXejemplo {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.base;
    requires org.controlsfx.controls;
    requires java.mail;
    requires commons.email;
    requires Reloj;
    requires org.docgene.help.jfx;


    exports com.martin;
    exports com.martin.Logica;
    exports com.martin.Models;
    exports com.martin.Views;
    exports com.martin.Utils;

    opens com.martin.Views to javafx.fxml;
}