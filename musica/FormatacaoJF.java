package musica;

//Concatena e retornas notas, pausa e mudanca de instrumentos na sintaxe do JFugue
public class FormatacaoJF {
    public static String nota(String nome, int oitava, char duracao, int volume) {
        return nome + oitava + duracao + "a" + volume + " ";
    }

    public static String pausa() {
        return "Rq ";
    }

    public static String mudaInstrumento(int novo) {
        return "I" + (novo - 1) + " ";
    }
}
