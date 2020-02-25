package martin;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmailsCarpetaFactory {
    public static List<EmailsCarpeta> createListCarpeta(){
        List<EmailsCarpeta> listaCarpeta = new ArrayList<>();
        listaCarpeta.add(new EmailsCarpeta("rrr", "ddd", "aaa", Date.valueOf(LocalDate.now()), "INBOX"));
        return listaCarpeta;
    }
}
