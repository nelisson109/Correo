package com.martin.Models;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import org.apache.commons.mail.util.MimeMessageParser;

public class EmailMensaje {
    private Message message;
    private MimeMessageParser parser;


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
 /*   public Object getContent(){
        Object obj = null;
        parser = new MimeMessageParser((MimeMessage) message);
        try {
            parser.parse();
            obj = parser.getMimeMessage();
        }catch(Exception e){
            e.printStackTrace();
        }
        return obj;
    }*/
    /*    try{
             String content = parser.getHtmlContent();
             if(content==null){
                 return parse.getPlainContent();
             }else{
                 return content;
             }
         }catch(Exception e){
             e.printStackTrace();
             return "";
         }*/
    public String getContent(){
        String content;
        parser = new MimeMessageParser((MimeMessage) message);
        try{
            parser.parse();
            content = parser.getHtmlContent();
            if (content==null){
                return parser.getPlainContent();
            }else{
                return content;
            }
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }


    }
 /*   public Object getContent(){viejo
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
    }*/

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
