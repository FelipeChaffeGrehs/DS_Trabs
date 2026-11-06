package regras;

import musica.ContextoChar;
import musica.FormatacaoJF;
import musica.ParametrosAtuais;

public class RegraDefault implements AnalisaChar {

    //Polimorfismo da testeChar, verifica se é qualquer outro caractere
    @Override
    public boolean testeChar(char c) {
        return true;
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        boolean repete = pAtual.isAnteriorEhNota();
        pAtual.setAnteriorEhNota(false);
        if (repete) { //Se ultimo contexto foi nota, repete a nota
            return FormatacaoJF.nota(pAtual.getUltimaNota(), pAtual.getOitava(), pAtual.getUltimaDuracao(), pAtual.getVolume());
        }
        return FormatacaoJF.pausa(); //Senao faz uma pausa
    }
}
