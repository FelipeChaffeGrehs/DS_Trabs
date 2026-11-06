package regras;

import musica.ContextoChar;
import musica.ParametrosAtuais;

//Interface que sera como um esqueleto para cada regra
public interface AnalisaChar {
    boolean testeChar(char c);
    String traducaoTexto(ContextoChar contexto, ParametrosAtuais pAtual);
}
