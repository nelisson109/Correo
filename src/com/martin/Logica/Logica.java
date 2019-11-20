package com.martin.Logica;


import com.martin.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {
    private ObservableList<EmailMensaje> listaMensajes = FXCollections.observableArrayList();
    private ObservableList<IniciarSesion> listaCuentas = FXCollections.observableArrayList();
    private EmailTreeItem nodoRoot;

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
    public ObservableList<IniciarSesion> getListaCuentas(){
        return listaCuentas;
    }

    public void cargarCuentas(IniciarSesion inicioCuenta){
        listaCuentas.add(inicioCuenta);
    }

    private int indice;
    private Store store;
    private Folder carpeta;

    public void cargarMensajes(){

        listaMensajes.clear();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        Store store;
        Folder folder = null;
        Message [] vectorMensajes;
        String usuario = listaCuentas.get(indice).getUsuario();
        String contraseña = listaCuentas.get(indice).getContraseña();

        try{
            store = sesion.getStore("imaps");
            store.connect("imap.googlemail.com", usuario, contraseña);
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

    public boolean conexion(){
        boolean respuesta;
        listaMensajes.clear();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        String usuario = listaCuentas.get(indice).getUsuario();
        String contraseña = listaCuentas.get(indice).getContraseña();
        try {
            store = sesion.getStore("imaps");
            store.connect("imap.googlemail.com", usuario, contraseña);
            respuesta = true;
            return respuesta;
        }catch(MessagingException e){
            e.printStackTrace();
            respuesta = false;
            return respuesta;
        }
    }

    public EmailTreeItem cargarCarpetas() throws MessagingException{
        nodoRoot = new EmailTreeItem(listaCuentas.get(indice), listaCuentas.get(indice).getUsuario(), carpeta);
        IniciarSesion is = null;
        String nombre = null;
        Folder carpeta = null;
        EmailTreeItem nodoVacio = new EmailTreeItem(is, nombre, carpeta);//nodo que tomara el nodo raiz como hijo
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        Store store = sesion.getStore("imaps");
        store.connect("imap.googlemail.com", listaCuentas.get(indice).getUsuario(), listaCuentas.get(indice).getContraseña());
        Folder [] vectorCarpetas = store.getDefaultFolder().list();
        //nodoRoot.setExpanded(true);lo comento porq hay qe cambiar el nodo

        nodoVacio.getChildren().add(nodoRoot);
        llenarCarpetas(vectorCarpetas, nodoVacio, listaCuentas.get(indice));
    /*    for(Folder folder : vectorCarpetas){
            if(folder.getType()!=0 && Folder.HOLDS_MESSAGES!=0){
                EmailTreeItem item = new EmailTreeItem(listaCuentas.get(0), folder.getName());
                nodoRoot.getChildren().add(item);//esto seria en el for
            }
            habra que meter un for para que vaya pillando las distintas cuentas
        }*/
        return nodoVacio;
    }
    public void llenarCarpetas(Folder [] vectorCarpetas, EmailTreeItem nodoRoot, IniciarSesion inicio) throws MessagingException{
        for (Folder folder : vectorCarpetas){
            //EmailTreeItem nodoRoot = new EmailTreeItem(listaCuentas.get(indice), listaCuentas.get(indice).getUsuario(), carpeta);
            EmailTreeItem item = new EmailTreeItem(listaCuentas.get(indice), folder.getName(), carpeta);
            nodoRoot.getChildren().add(item);
            if(folder.getType()==Folder.HOLDS_FOLDERS){
                llenarCarpetas(folder.list(), item, listaCuentas.get(indice));
            }
        }

    }
    /*
    * aqui hay que crear un metodo que devuelva el mensaje del correo con todos sus elementos
    * Habra que parsearlo con MineMessageParser
    * habra que crear un metodo que sea public EmailTreeItem cargarCarpetas(IniciarSesion inicio)
    * habra que poner que el EmailTreeItem extienda de TreeItem<String>
    * en el constructor del EmailTreeItem le pasaremos un super(con el objeto de carpeta)
    * 0.Yo al final devuelvo el EmailTreeItem raiz
    *1.conectar store
    * 2.obtener carpetas primer nivel(for)
    * 3.crear treeItem por cada carpeta(if)
    * 4.Añadir los hijos a la raiz
    * 5.Solo nos quedaría la recursividad(llamar dentro del metodo al propio metodo)*/


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