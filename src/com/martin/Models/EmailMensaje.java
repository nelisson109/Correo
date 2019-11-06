package com.martin.Models;

import javax.mail.Message;
import javax.mail.MessagingException;

public class EmailMensaje {
    private Message message;

    public EmailMensaje(Message message) {
        this.message = message;
    }
    public String getFrom(){
        try{
            if(message.getFrom()[0].toString())
                return message.getFrom()[0].toString();
        }catch (MessagingException e){
            e.printStackTrace();
            return
        }
    }
}
