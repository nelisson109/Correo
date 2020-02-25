package com.martin.Logica;


import com.martin.Models.*;
import com.sun.mail.util.MailSSLSocketFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.web.HTMLEditor;
import martin.Tarea;
import org.apache.commons.mail.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {
    private ObservableList<EmailMensaje> listaMensajes = FXCollections.observableArrayList();
    private ObservableList<IniciarSesion> listaCuentas = FXCollections.observableArrayList();
    private EmailTreeItem nodoVacio;
    private ArrayList<IniciarSesion> misCuentas = new ArrayList<>();
    private ArrayList<IniciarSesion> misCuentas2 = new ArrayList<>();
    private ObjectInputStream lectura;
    private ObjectOutputStream escritura;
    private ObservableList<Tarea> listaTareas = FXCollections.observableArrayList();

    private static Logica INSTANCE = null;

    private Logica() {
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<EmailMensaje> getListaMensajes() {
        return listaMensajes;
    }

    public ObservableList<IniciarSesion> getListaCuentas() {
        return listaCuentas;
    }

    public ObservableList<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void añadirTarea(Tarea tarea) {
        listaTareas.add(tarea);
    }

    public void borrarTarea(Tarea tarea) {
        listaTareas.remove(tarea);
    }

    public void cargarCuentas(IniciarSesion inicioCuenta) {
        listaCuentas.add(inicioCuenta);
    }

    public void borrarCuenta(IniciarSesion cuenta) {
        listaCuentas.remove(cuenta);
    }

    private int indice = 0;
    private Store store;
    private Folder carpeta;

    public void escribirObjetos(File fichero) {

        try {
            for (IniciarSesion i : misCuentas) {
                misCuentas2.add(i);
            }
            escritura = new ObjectOutputStream(new FileOutputStream(fichero));
            escritura.writeObject(misCuentas2);
        } catch (FileNotFoundException e) {
            e.getMessage();
            System.out.println("Error. No se encuentra el fichero");
        } catch (IOException e) {
            e.getMessage();
        } finally {
            try {
                if (escritura != null) {
                    escritura.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el fichero para escritura");
            }
        }
    }

    public void leerObjetos(File fichero) {
        try {
            lectura = new ObjectInputStream(new FileInputStream(fichero));
            misCuentas2 = (ArrayList<IniciarSesion>) lectura.readObject();
            for (IniciarSesion i : misCuentas2) {
                misCuentas.add(i);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero para leer");
            e.getLocalizedMessage();
        } catch (IOException e) {
            e.getMessage();
            System.out.println("Error de entrada/salida");
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha encontrado la clase");
            e.getLocalizedMessage();
        } finally {
            try {
                if (lectura != null) {
                    lectura.close();
                }
            } catch (IOException e) {
                e.getMessage();
                System.out.println("Error al cerrar el fichero");
            }
        }
    }

    public ObservableList<EmailMensaje> cargarMensajes(Folder folder) {

        listaMensajes.clear();
        try {
            if (folder != null && folder.getType() == 3) {
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


    public void borrarMensaje(EmailMensaje mensaje, EmailTreeItem item) {
        if (item.getFolder().toString().equals("[Gmail]/Papelera")) {
            try {
                mensaje.getMensaje().setFlag(Flags.Flag.DELETED, true);
                item.getFolder().close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            Message[] vectorMensaje = new Message[]{mensaje.getMensaje()};
            Folder papelera = null;
            try {
                papelera = item.getStore().getFolder("[Gmail]/Papelera");
                item.getFolder().copyMessages(vectorMensaje, papelera);
                item.getFolder().close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean conexion(IniciarSesion inicio) {
        boolean respuesta;
        listaMensajes.clear();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", inicio.getUsuario());
        props.put("mail.smtp.clave", inicio.getContraseña());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
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
        } catch (MessagingException e) {
            e.printStackTrace();
            respuesta = false;
            return respuesta;
        }
    }

    private Session getSession(IniciarSesion cuenta) {
        Properties props = new Properties();
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        props.put("mail.imaps.ssl.trust", "*");
        props.put("mail.imaps.ssl.socketFactory", sf);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String emailUser = cuenta.getUsuario();
        String pass = cuenta.getContraseña();
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUser, pass);
            }
        });
        return session;
    }

    public EmailTreeItem cargarCarpetas() throws MessagingException {
        EmailTreeItem nodoPadre = new EmailTreeItem(null, null, null, null);
        for (int i = 0; i < listaCuentas.size(); i++) {
            EmailTreeItem itemCuenta = new EmailTreeItem(listaCuentas.get(i), listaCuentas.get(i).getUsuario(), null, listaCuentas.get(i).getStore());
            nodoPadre.getChildren().add(itemCuenta);
            try {
                Folder[] vectorCarpetas = listaCuentas.get(i).getStore().getDefaultFolder().list();
                itemCuenta.setExpanded(true);
                llenarCarpetas(vectorCarpetas, itemCuenta, listaCuentas.get(i));
            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return nodoPadre;
    }

    public void llenarCarpetas(Folder[] vectorCarpetas, EmailTreeItem itemCuenta, IniciarSesion inicio) throws MessagingException {
        for (Folder folder : vectorCarpetas) {
            //EmailTreeItem nodoRoot = new EmailTreeItem(listaCuentas.get(indice), listaCuentas.get(indice).getUsuario(), carpeta);
            EmailTreeItem item = new EmailTreeItem(inicio, folder.getName(), folder, inicio.getStore());
            itemCuenta.getChildren().add(item);
            if (folder.list().length > 0) {
                llenarCarpetas(folder.list(), itemCuenta, inicio);
            }
        }

    }

    public void escribirCorreo(IniciarSesion cuenta, String desde, String para, String asunto, HTMLEditor contenido) {
        boolean conection;

        String contraseña;
        for (int i = 0; i < listaCuentas.size(); i++) {
            if (listaCuentas.get(i).getUsuario().equals(desde)) {
                cuenta = getListaCuentas().get(i);
            }
        }
//    cuenta = Logica.getInstance().getListaCuentas().get(0);

        // conection = conexion(cuenta);
        // if (conection) {
        try {
            Session a = getSession(cuenta);
            MimeMessage message = new MimeMessage(a);
            message.setFrom(new InternetAddress(desde));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
            message.setSubject(asunto);
            // message.setText(contenido);
            message.setContent(contenido.getHtmlText(), "text/html");
                /*Transport transport = cuenta.getSession().getTransport("smtp");
                transport.connect(cuenta.getUsuario(), cuenta.getContraseña());
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();*/
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //}
    }


}