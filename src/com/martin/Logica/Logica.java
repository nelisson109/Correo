package com.martin.Logica;


import com.martin.Models.Cuenta;
import com.martin.Models.EmailMensaje;
import com.martin.Models.IniciarSesion;
import com.martin.Models.MensajeCorreo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;

import javax.mail.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {
    private ObservableList<EmailMensaje> listaMensajes = FXCollections.observableArrayList();//esta mal, no se le puede pasar Message, hay que pasarle EmailMensaje
    private ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();
    private MensajeCorreo mensaje;

    private static Logica INSTANCE = null;

    private Logica() {

    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }
    public ObservableList<EmailMensaje> getListaMensajes(){
        return listaMensajes;
    }

    public void cargarMensajes(){
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        Store store;
        Folder folder = null;
        Message [] vectorMensajes;
        try{
            store = sesion.getStore("imaps");
            store.connect("imap.googlemail.com", "martinlg36dam@gmail.com", "helipi67");
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            vectorMensajes = folder.getMessages();
            for(int i=0; i<vectorMensajes.length; i++){
                EmailMensaje mensaje = new EmailMensaje(vectorMensajes[i]);
                listaMensajes.add(mensaje);
            }
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }


/*--------------------------------------------
    public void addPartido(Partido partido) {
        partidos.add(partido);
    }
    public void borrarPartido(Partido partido){
        partidos.remove(partido);
    }
    public void modificarPartido(Partido partidoModificar){
        int posicion = partidos.indexOf(partidoModificar);
        partidos.set(posicion, partidoModificar);
    }
    public ObservableList<Partido> getPartidos() {
        return partidos;
    }
   ------------------------------------------------ */
}