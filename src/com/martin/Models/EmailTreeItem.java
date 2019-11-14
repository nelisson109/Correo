package com.martin.Models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {
    private IniciarSesion iniciarSesion;
    private String nombre;
    private Folder carpeta;

    public EmailTreeItem(IniciarSesion iniciarSesion, String nombre) {
        super(nombre);
        this.iniciarSesion = iniciarSesion;
        this.nombre = nombre;
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
}
