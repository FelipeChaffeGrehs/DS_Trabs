package regras;

import musica.ContextoChar;
import musica.FormatacaoJF;
import musica.ParametrosAtuais;

public class TrocaInstrumento implements AnalisaChar {

    private static final int BANDONEON = 24;
    private static final int AGOGO = 114;
    private static final int TUBULAR_BELLS = 15;
    private static final int TOTAL_INSTRUMENTOS = 128;

    //Polimorfismo da testeChar, verifica se é um dos caracteres que trocam de instrumento
    @Override
    public boolean testeChar(char c) {
        return "!,;0123456789".indexOf(c) >= 0;
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        int novo = switch (contexto.atual) {
            case '!' -> BANDONEON;
            case ',' -> AGOGO;
            case ';', '1', '3', '5', '7', '9' -> TUBULAR_BELLS;
            //Se for algarismo par, ele eh somado ao numero atual do instrumento
            case '0', '2', '4', '6', '8' -> pAtual.getNumInstrumento() + (contexto.atual - '0');
            default -> throw new IllegalStateException("Caractere inesperado: " + contexto.atual);
        };

        //Ajusta o numero do instrumento para que ele fique entre 1 e TOTAL_INSTRUMENTOS
        novo = (novo - 1) % TOTAL_INSTRUMENTOS + 1;
        pAtual.setNumInstrumento(novo);
        pAtual.setAnteriorEhNota(false);
        return FormatacaoJF.mudaInstrumento(novo);
    }
}
