package martin;

import com.martin.Models.IniciarSesion;

import java.util.ArrayList;
import java.util.List;

public class EmailsCuentaFactory {
    public static List<IniciarSesion> createListUsuarios(){
        List<IniciarSesion> listaCuentas = new ArrayList<>();
        listaCuentas.add(new IniciarSesion("martinlg36dam@gmail.com", "helipi67"));
        return listaCuentas;
    }
}
