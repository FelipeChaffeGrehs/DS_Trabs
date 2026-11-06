package musica;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import regras.AnalisaChar;

public class Tradutor {

    private final List<AnalisaChar> regras;

    //Construtor da classe, recebe uma lista de regras de mapeamento
    public Tradutor(List<AnalisaChar> regras) {
        this.regras = regras;
    }

    /*Aqui temos dois metodos com o mesmo nome, mas parametros diferentes.
    Isso foi feito para definir como padrão o instrumento musical em 7,
    o volume inicial em 100 e a oitava inicial em 6*/
    public String traduzir(String texto, int bpmInicial) {
        return traduzir(texto, bpmInicial, 7, 100, 6);
    }


    public String traduzir(String texto, int bpmInicial, int instrumentoInicial, int volumeInicial, int oitavaInicial) {
        //Separa o texto sempre que há quebra de linha em um array de linhas
        String[] linhas = texto.split("\\R");

        //Checa se a entrada tem linhas ≤ numero de vozes de maximo
        if (linhas.length > Vozes.MAXIMO_DE_VOZES) {
            throw new IllegalArgumentException("O texto tem " + linhas.length + " linhas, mas o máximo é " + Vozes.MAXIMO_DE_VOZES + " vozes.");
        }

        //Prepara a musica com o bpm inicial
        StringBuilder musica = new StringBuilder("T" + bpmInicial + " ");

        //Registra o bpm atual como o bpm inicial passado de parametro
        int bpmAtual = bpmInicial;

        //Para cada linha (voz) executa as funcoes abaixo
        for (int i = 0; i < linhas.length; i++) {
            //Cria a voz (oitava, volume e instrumento) de cada linha
            ParametrosAtuais parametros = Vozes.criar(i, instrumentoInicial, volumeInicial, oitavaInicial);

            //Configura o BPM dos parametros de cada voz com o bpm atual
            parametros.setBPM(bpmAtual);

            //Concatena na musica qual voz esta tocando (V0, V1, V2, V3)
            musica.append("V").append(i).append(" ");

            //Concatena na musica a traducao de cada linha
            musica.append(traduzirLinha(linhas[i], parametros));
            bpmAtual = parametros.getBPM();
        }
        return musica.toString().trim();
    }

    //Pattern que verifica se tem algum atraso Ex: [3]
    private static final Pattern ATRASO = Pattern.compile("^\\s*\\[(\\d{1,3})]\\s*");
    private String traduzirLinha(String linha, ParametrosAtuais parametros) {
        //Cria StringBuilder para concatenar todas instruções de uma voz
        StringBuilder voz = new StringBuilder();

        //Concatena a instrução de instrumento Ex: I12
        voz.append("I").append(parametros.getNumInstrumento() - 1).append(" ");

        //Busca o padrao do pattern na String da linha recebida como parametro
        Matcher atraso = ATRASO.matcher(linha);

        /*Se acha um caso de atraso, concatena o numero de silencios desejados
          Ex: [4] adiciona 4 silencios no comeco */
        if (atraso.find()) {
            int beats = Integer.parseInt(atraso.group(1));
            voz.append("Rq ".repeat(beats));

            //Tira [3] da linha para nao ser traduzida posteriormente
            linha = linha.substring(atraso.end());
        }

        //Chama o metodo separar da classe Selecionador e faz um for na lista de Strings retornada
        for (String pedaco : Selecionador.separar(linha)) {
            ContextoChar contexto = new ContextoChar(pedaco);
            //Pega a lista de regras passadas no construtor e ve qual é a regra de cada pedaco
            for (AnalisaChar regra : regras) {
                if (regra.testeChar(contexto.atual)) {
                    voz.append(regra.traducaoTexto(contexto, parametros));
                    break;
                }
            }
        }
        return voz.toString(); //Transforma a StringBuilder de volta a String
    }
}
