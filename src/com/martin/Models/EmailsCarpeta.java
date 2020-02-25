package com.martin.Models;

import java.util.Date;

public class EmailsCarpeta {
    private String remitente;
    private String destinatario;
    private String asunto;
    private Date fecha;
    private String carpeta;

    public EmailsCarpeta(String remitente, String destinatario, String asunto, Date fecha, String carpeta) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.fecha = fecha;
        this.carpeta = carpeta;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }
}
