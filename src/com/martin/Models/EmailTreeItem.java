package com.martin.Models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import javax.mail.Store;

public class EmailTreeItem extends TreeItem<String> {
    private IniciarSesion iniciarSesion;
    private String nombre;
    private Folder carpeta;
    private Store store;

    public EmailTreeItem(IniciarSesion iniciarSesion, String nombre, Folder carpeta, Store store) {
        super(nombre);
        this.iniciarSesion = iniciarSesion;
        this.nombre = nombre;
        this.carpeta = carpeta;
        this.store = store;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public EmailTreeItem devolver(){
        EmailTreeItem nodoPrincipal = null;

        return nodoPrincipal;
    }
    public Folder getFolder(){
        return carpeta;
    }

    public Store getStore() {
        return store;
    }
}
