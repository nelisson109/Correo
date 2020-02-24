package martin;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmailFactory {
    public static List<Email> createListEmail(){
        List<Email> listaEmails = new ArrayList<>();
        listaEmails.add(new Email("remitente", "asunto", "contenido", Date.valueOf(LocalDate.now())));
        return listaEmails;
    }
}
