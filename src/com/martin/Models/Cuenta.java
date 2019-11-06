package com.martin.Models;

public class Cuenta {
    private IniciarSesion sesion;
    private String titular;

    public Cuenta(IniciarSesion sesion, String titular) {
        this.sesion = sesion;
        this.titular = "Martin Loza";
    }

    public IniciarSesion getSesion() {
        return sesion;
    }

    public void setSesion(IniciarSesion sesion) {
        this.sesion = sesion;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
