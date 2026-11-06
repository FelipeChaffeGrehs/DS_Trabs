package regras;

import musica.ContextoChar;
import musica.Duracao;
import musica.FormatacaoJF;
import musica.ParametrosAtuais;

public class Nota implements AnalisaChar {

    //Polimorfismo da testeChar, verifica se é algum dos caracteres
    @Override
    public boolean testeChar(char c) {
        return ("ABCDEFG".indexOf(c) >= 0);
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        String nota = Duracao.tirarDuracao(contexto.pedaco); //Se tiver ':', tira
        char duracao = Duracao.letraDeDuracao(contexto.pedaco);//Se tiver ':' obtem a duracao musical

        pAtual.setUltimaNota(nota);
        pAtual.setUltimaDuracao(duracao);
        pAtual.setAnteriorEhNota(true);
        //Retorna a nota na sintaxe do JFugue
        return FormatacaoJF.nota(nota, pAtual.getOitava(), duracao, pAtual.getVolume());
    }
}
