package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

public class Oitava implements AnalisaChar {

    //Polimorfismo da testeChar, verifica se é algum dos caracteres
    @Override
    public boolean testeChar(char c) {
        return switch (c) {
            case '.', '?', 'V' -> true;
            default -> false;
        };
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        if (contexto.atual == 'V') {
            pAtual.descerOitava();
        } else {
            pAtual.subirOitava();
        }
        pAtual.setAnteriorEhNota(false);
        return ""; //Retorna String vazia, pois apenas muda oitava
    }
}
