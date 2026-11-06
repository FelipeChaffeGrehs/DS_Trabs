package regras;

import java.util.ArrayList;
import java.util.List;

public class InicializaRegras {
    //Inicializa a lista de regras que serao passadas como parametro para o tradutor
    public static List<AnalisaChar> criarRegras() {
        List<AnalisaChar> regras = new ArrayList<>();
        regras.add(new Nota());
        regras.add(new Silencio());
        regras.add(new TrocaInstrumento());
        regras.add(new Volume());
        regras.add(new Oitava());
        regras.add(new Andamento());
        regras.add(new RegraDefault());
        return regras;
    }
}
