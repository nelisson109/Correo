package com.martin.Logica;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Logica {
  /*  private ObservableList<Partido> partidos = FXCollections.observableArrayList();
    private ArrayList<Partido> partidos1 = new ArrayList<>();
    private ArrayList<Partido> partidos2 = new ArrayList<Partido>();*/
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
    public PasswordField getPfContraseña(PasswordField pfContraseña){
        return pfContraseña;
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