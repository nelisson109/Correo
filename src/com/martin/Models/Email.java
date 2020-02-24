package com.martin.Models;

import java.util.Date;

public class Email {
    private String remitente;
    private String asunto;
    private String contenido;
    private Date fecha;

    public Email(String remitente, String asunto, String contenido, Date fecha) {
        this.remitente = remitente;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
