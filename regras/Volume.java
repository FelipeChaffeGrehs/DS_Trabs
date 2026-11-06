package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

public class Volume implements AnalisaChar {

    //Polimorfismo da testeChar, verifica se é espaco
    @Override
    public boolean testeChar(char c) {
        return c == ' ';
    }

    //Polimorfismo da traducaoTexto, executa o comando de cada regra
    @Override
    public String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual) {
        //Se for espaco, dobra o volume ou define como maximo (127)
        pAtual.setVolume(Math.min(pAtual.getVolume() * 2, 127));
        pAtual.setAnteriorEhNota(false);
        return "";
    }
}
