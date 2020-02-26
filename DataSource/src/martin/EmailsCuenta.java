package martin;

import com.sun.mail.imap.IMAPFolder;

import javax.mail.MessagingException;
import javax.mail.Store;

public class EmailsCuenta {
    private String usuario;
    private String contraseña;
    private Store store;

    public EmailsCuenta(String usuario, String contraseña, Store store) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.store = store;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public IMAPFolder getImapFolder (String usuario){
        IMAPFolder folder = null;
        try {
            folder = (IMAPFolder) store.getFolder(usuario);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return folder;
    }
}
