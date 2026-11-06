package regras;

import musica.ContextoChar;
import musica.FormatacaoJF;
import musica.ParametrosAtuais;

public class Silencio implements AnalisaChar {

    //Polimorfismo da testeChar, verifica se é algum dos caracteres em abcdefg
    @Override
    public boolean testeChar(char c) {
        return ("abcdefg".indexOf(c) >= 0);
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        pAtual.setAnteriorEhNota(false);
        return FormatacaoJF.pausa();
    }
}
