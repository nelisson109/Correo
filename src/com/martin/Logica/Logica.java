package com.martin.Logica;


import com.martin.Models.Cuenta;
import com.martin.Models.EmailMensaje;
import com.martin.Models.IniciarSesion;
import com.martin.Models.MensajeCorreo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;

import javax.mail.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Logica {
  /*  private ObservableList<Partido> partidos = FXCollections.observableArrayList();
    private ArrayList<Partido> partidos1 = new ArrayList<>();
    private ArrayList<Partido> partidos2 = new ArrayList<Partido>();*/
    private ObservableList<MensajeCorreo> listaMensajes = FXCollections.observableArrayList();//esta mal, no se le puede pasar Message, hay que pasarle EmailMensaje
    private ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList();
    private MensajeCorreo mensaje;
    private ObjectInputStream lectura;
    private ObjectOutputStream escritura;
    private static Logica INSTANCE = null;

    private Logica() {

    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }
    public ObservableList<MensajeCorreo> getMensaje(){
        return listaMensajes;
    }

    public void añadirMensajes(IniciarSesion inicio){
        mensaje = new MensajeCorreo();
        mensaje.mostrarMensaje(inicio);
        for (int i=0; i<mensaje.getMessage().length; i++){
            listaMensajes.add(mensaje.getMessage()[i]);
        }

    }


/*
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
    */
}