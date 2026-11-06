package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

public class Andamento implements AnalisaChar {

    private static final int VARIACAO = 10;
    private static final int BPM_MINIMO = 20;
    private static final int BPM_MAXIMO = 300;

    //Polimorfismo da testeChar, verifica se é algum dos caracteres
    @Override
    public boolean testeChar(char c) {
        return c == '>' || c == '<';
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        int variacao = (contexto.atual == '>') ? VARIACAO : -VARIACAO;
        //Calcula o novo bpm e garante nao ser maior que o maximo e menor que o minimo
        int novo = Math.max(BPM_MINIMO,
                Math.min(BPM_MAXIMO, pAtual.getBPM() + variacao));
        pAtual.setBPM(novo);
        pAtual.setAnteriorEhNota(false);
        return "T" + novo + " ";
    }
}
