package com.martin.Models;

import javax.mail.*;
import java.util.Properties;

public class MensajeCorreo {//esta clase sobra en principio
    private Properties props;
    private Session session;
    private Store store;
    private Folder folder;
    private Message[] messages = null;
    private MensajeCorreo mensaje;

    public void mostrarMensaje(IniciarSesion inicio){
        props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        session = Session.getInstance(props);

        try{
            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", inicio.getTfCorreo().toString(), inicio.getPfContraseña().toString());
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            messages = folder.getMessages();
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }
    public Message [] getMessage(){
        return messages;
    }

}
