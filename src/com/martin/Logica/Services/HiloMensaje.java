package com.martin.Logica.Services;

import com.martin.Logica.Logica;
import com.martin.Models.EmailMensaje;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;


public class HiloMensaje extends Service<ObservableList<EmailMensaje>> {

    private Folder folder;
    public HiloMensaje(Folder folder){
        this.folder = folder;
    }
    protected Task<ObservableList<EmailMensaje>> createTask(){
        return new Task<>() {
            @Override
            protected ObservableList<EmailMensaje> call() throws Exception {
                return Logica.getInstance().cargarMensajes(folder);
            }
        };
    }
}
