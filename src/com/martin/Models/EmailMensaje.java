package com.martin.Models;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.mail.util.MimeMessageParser;

public class EmailMensaje {
    private Message message;
    private MimeMessageParser parser;
    /* ESCRIBIR NO LEIDOS EN NEGRITA
    * en esta clase hay qe crear un metodo q devuelva boolean estaLeido()
    * Flag.SEEN
    * return message.isSet(Flag.SEEN)
    * en el inicialize del main:
    * tableview de emails.setRowFactory(new Callback...
    * return new TableRow<EmailMesage>
    protected void updateItem(EmailMessage comosellame, boolean e)
    * if(emailMensaje!=null){
    * if(emailMessage.isRead())
    *   setStyle("-fx-font-weight:blod")
    * else
    * setStyle("");*/

    public boolean isRead(){
        try{
            return message.isSet(Flags.Flag.SEEN);
        }catch(MessagingException e){
            e.printStackTrace();
        }
        return true;
    }

    public Message getMensaje(){
        return message;
    }


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

    public String getTo() throws MessagingException{
        Address [] to = message.getReplyTo();
        String desde = to[0].toString();
        return desde;
    }

    public Date getFecha(){
        Date date = null;
        try {
            date = message.getReceivedDate();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return date;
    }

}
