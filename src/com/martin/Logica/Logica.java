package com.martin.Logica;


import com.martin.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import javax.mail.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {
    private ObservableList<EmailMensaje> listaMensajes = FXCollections.observableArrayList();
    private ObservableList<IniciarSesion> listaCuentas = FXCollections.observableArrayList();
    private EmailTreeItem nodoVacio;

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
    public void borrarCuenta(IniciarSesion cuenta){
        listaCuentas.remove(cuenta);
    }

    private int indice = 0;
    private Store store;
    private Folder carpeta;

    public void cargarMensajes(Folder folder){

        listaMensajes.clear();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        Store store;
       // Folder folder = null;
        Message [] vectorMensajes = null;
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
    public TreeItem actualizarTree() throws MessagingException {//hay que hacer un metodo que nos cargue el arbol de cada cuenta
        TreeItem nodoRaizPadre = new TreeItem("Correos");

        for(int i=0; 0<listaCuentas.size(); i++){//llamar al cerrar la ventana del login y asignarselo al treeview
            conexion(listaCuentas.get(i));
            nodoRaizPadre.setExpanded(true);
            nodoRaizPadre.getChildren().add(cargarCarpetas());
        }
        return nodoRaizPadre;
    }

    public boolean conexion(IniciarSesion inicio){
        boolean respuesta;
        listaMensajes.clear();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
       // String usuario = listaCuentas.get(indice).getUsuario();
        //String contraseña = listaCuentas.get(indice).getContraseña();
        try {
            store = sesion.getStore("imaps");
          // store.connect("imap.googlemail.com", usuario, contraseña);
            store.connect("imap.googlemail.com", inicio.getUsuario(), inicio.getContraseña());
            respuesta = true;
            return respuesta;
        }catch(MessagingException e){
            e.printStackTrace();
            respuesta = false;
            return respuesta;
        }
    }
 /*   public void llenandoCarpetas(Folder [] vectorCarpetas, EmailTreeItem nodoRoot, IniciarSesion inicio) throws MessagingException{
        EmailTreeItem nodoRoot = new EmailTreeItem(listaCuentas.get(indice), listaCuentas.get(indice).getUsuario(), carpeta);
        IniciarSesion is = null;
        String nombre = null;
        Folder carpeta = null;
        nodoVacio = new EmailTreeItem(is, nombre, carpeta);//nodo que tomara el nodo raiz como hijo
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        Store store = sesion.getStore("imaps");
        store.connect("imap.googlemail.com", listaCuentas.get(indice).getUsuario(), listaCuentas.get(indice).getContraseña());
        Folder [] vectorCarpetas = store.getDefaultFolder().list();
        //nodoRoot.setExpanded(true);lo comento porq hay qe cambiar el nodo

        nodoVacio.getChildren().add(nodoRoot);
        for (Folder folder : vectorCarpetas){
            EmailTreeItem item = new EmailTreeItem(listaCuentas.get(indice), folder.getName(), carpeta);
            nodoRoot.getChildren().add(item);
            if(folder.getType()==Folder.HOLDS_FOLDERS){
                llenarCarpetas(folder.list(), item, listaCuentas.get(indice));
            }
        }

    }*/
    public EmailTreeItem cargarCarpetas() throws MessagingException{
        EmailTreeItem nodoRoot = new EmailTreeItem(listaCuentas.get(indice), listaCuentas.get(indice).getUsuario(), carpeta);
        IniciarSesion is = null;
        String nombre = null;
        Folder carpeta = null;
        nodoVacio = new EmailTreeItem(is, nombre, carpeta);//nodo que tomara el nodo raiz como hijo
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(props);
        Store store = sesion.getStore("imaps");
        store.connect("imap.googlemail.com", listaCuentas.get(indice).getUsuario(), listaCuentas.get(indice).getContraseña());
        Folder [] vectorCarpetas = store.getDefaultFolder().list();
        //nodoRoot.setExpanded(true);lo comento porq hay qe cambiar el nodo

        nodoVacio.getChildren().add(nodoRoot);
        llenarCarpetas(vectorCarpetas, nodoRoot, listaCuentas.get(indice));
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
 /*   public ObservableList<EmailMensaje> cogerCarpetas(Folder carpeta, IniciarSesion inicio){
        ObservableList<EmailMensaje> lista = FXCollections.observableArrayList();
        try{
            if(carpeta!=null && carpeta.getType()==3){
                if (!carpeta.isOpen())
                    carpeta.open(Folder.READ_WRITE);
                for (Message message : carpeta.getMessages()){
                    lista.add(new EmailMensaje(message));
                }
            }
        }catch(MessagingException e){
            e.printStackTrace();
        }
        return lista;
    }*/
    /*
    public ObservableList<EmailMensaje> getEmailsFolder(Folder folder, EmailAcount loqesea){
        ObservableList<EmailMensaje> lista = FXCollections.observableArrayList();
        try{
            if(folder!null && folder.getType()==3){
                if (!folder.isOpen()){
                    folder.open(Folder.READ_WRITE);
                }
                for (Message message : folder.getMessages()){
                    lista.add(new EmailMensaje(message, loqesea));
                }
            }
        }catch()
    }
    borrar:
    Folder trashFolder = emailAconunt.getStore().getFolder"[Gmail]/Papelera);
    if(currentFolder.getFullName().equals(trashFolder.getFullName())
        emailmessage.delete();


    Nuevo:
    crear clase getmessageservice
    extender de Service y tipar con lo que devuelva el método dentro del extends
    dentro de esta clase, tendremos un protected Task<Observable...<EmailMessage> createTask
    dentro del tak llamar al metodo Logica.getInstance().getEmailsFolders(folder, loquesea);
    en el contructor de mi servicio meto los parametros ....falta
    luego desde el controler principal crearemos un objeto de esta clase, lo arrancamos con el metodo start() y le metemos el metodo setOnSucceded, dentro de este:
    tableviw.setItems(getMessagesServicies.getValue() y processIndicator
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