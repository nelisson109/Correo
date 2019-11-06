package com.martin.Models;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public class EmailMensaje {
    private Message message;

    public EmailMensaje(Message message) {
        this.message = message;
    }
    public String getSubject(){
        String subject = null;
        try{
            subject = message.getSubject();
        }catch (MessagingException e){
            e.printStackTrace();
        }
        return subject;
    }

    public Object getContent(){
        Object obj = null;
        try{
            obj = message.getContent();
        }catch (MessagingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error de entrada/salida");
        }
        return obj;
    }

    public String getFrom(){
        Address[] direccion = null;
        try{
            direccion = message.getFrom();
        }catch(MessagingException e){
            e.printStackTrace();
        }
        String cadena = String.valueOf(direccion[0]);
        return cadena;
    }
}
