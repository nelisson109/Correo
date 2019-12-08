package com.martin.Logica;


import com.martin.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import org.apache.commons.mail.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

    public ObservableList<EmailMensaje> cargarMensajes(Folder folder){

        listaMensajes.clear();
        try {
            if (folder!=null && folder.getType()==3) {
                if (!folder.isOpen())
                    folder.open(Folder.READ_WRITE);

                Message[] vectorMensajes = folder.getMessages();
                for (int i = 0; i < vectorMensajes.length; i++) {
                    EmailMensaje mensaje = new EmailMensaje(vectorMensajes[i]);
                    listaMensajes.add(mensaje);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return listaMensajes;
    }
    public void borrarMensaje(EmailMensaje mensaje, EmailTreeItem item){
        if (item.getFolder().toString().equals("[Gmail]/Papelera")){
            try {
                mensaje.getMensaje().setFlag(Flags.Flag.DELETED, true);
                item.getFolder().close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }else{
            Message [] vectorMensaje = new Message[]{mensaje.getMensaje()};
            Folder papelera = null;
            try {
                papelera = item.getStore().getFolder("[Gmail]/Papelera");
                item.getFolder().copyMessages(vectorMensaje, papelera);
                item.getFolder().close();
            }catch (MessagingException e){
                e.printStackTrace();
            }
        }
    }

    public boolean conexion(IniciarSesion inicio){
        boolean respuesta;
        listaMensajes.clear();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.smarttls.enable", "true");
        Session sesion = Session.getInstance(props);
       // String usuario = listaCuentas.get(indice).getUsuario();
        //String contraseña = listaCuentas.get(indice).getContraseña();
        try {
            store = sesion.getStore("imaps");
          // store.connect("imap.googlemail.com", usuario, contraseña);
            store.connect("imap.googlemail.com", inicio.getUsuario(), inicio.getContraseña());
            inicio.setStore(store);
            respuesta = true;
            return respuesta;
        }catch(MessagingException e){
            e.printStackTrace();
            respuesta = false;
            return respuesta;
        }
    }
    public EmailTreeItem cargarCarpetas() throws MessagingException{
       EmailTreeItem nodoPadre = new EmailTreeItem(null, null, null, null);
       for (int i=0; i<listaCuentas.size(); i++){
           EmailTreeItem itemCuenta = new EmailTreeItem(listaCuentas.get(i), listaCuentas.get(i).getUsuario(), null, listaCuentas.get(i).getStore());
           nodoPadre.getChildren().add(itemCuenta);
           try{
               Folder [] vectorCarpetas = listaCuentas.get(i).getStore().getDefaultFolder().list();
               itemCuenta.setExpanded(true);
               llenarCarpetas(vectorCarpetas, itemCuenta, listaCuentas.get(i));
           }catch (MessagingException e){
               e.printStackTrace();
               return null;
           }
       }
       return nodoPadre;
    }
    public void llenarCarpetas(Folder [] vectorCarpetas, EmailTreeItem itemCuenta, IniciarSesion inicio) throws MessagingException{
        for (Folder folder : vectorCarpetas){
            //EmailTreeItem nodoRoot = new EmailTreeItem(listaCuentas.get(indice), listaCuentas.get(indice).getUsuario(), carpeta);
            EmailTreeItem item = new EmailTreeItem(inicio, folder.getName(), folder, inicio.getStore());
            itemCuenta.getChildren().add(item);
            if(folder.list().length>0){
                llenarCarpetas(folder.list(), itemCuenta, inicio);
            }
        }

    }

    public void escribirCorreo(String desde, String para, String asunto, String contenido){
        IniciarSesion cuenta = null;
        String contraseña;
        for(int i=0; i<listaCuentas.size(); i++){
            if (listaCuentas.get(i).getUsuario().equals(desde)){
                contraseña = listaCuentas.get(i).getContraseña();
                cuenta = listaCuentas.get(i);
            }
        }
        try {
            MimeMessage message = new MimeMessage(cuenta.getSession());
            message.setFrom(new InternetAddress(desde));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
            message.setSubject(asunto);
            message.setText(contenido);
            // message.setContent(contenido);
            Transport transport = cuenta.getSession().getTransport("smtp");
            transport.connect(cuenta.getUsuario(), cuenta.getContraseña());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

}