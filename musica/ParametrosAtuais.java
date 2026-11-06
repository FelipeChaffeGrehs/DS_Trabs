package musica;

public class ParametrosAtuais {

    private boolean anteriorEhNota = false;
    private int volume = 64;
    private String ultimaNota = null;
    private int numInstrumento;
    private char ultimaDuracao;
    private int bpm;
    public static final int OITAVA_MINIMA = 0;
    public static final int OITAVA_MAXIMA = 9;

    private final int oitavaBase;
    private int oitava;

    public ParametrosAtuais(int oitavaBase) {
        this.oitavaBase = oitavaBase;
        this.oitava = oitavaBase;
    }

    //Se nova oitava ultrapassar o limite máximo das oitavas, ele retorna a oitava base
    public void subirOitava() {
        oitava = (oitava + 1 > OITAVA_MAXIMA) ? oitavaBase : oitava + 1;
    }

    //Se nova oitava for menor que o limite minimo das oitavas, ele retorna a oitava base
    public void descerOitava() {
        oitava = (oitava - 1 < OITAVA_MINIMA) ? oitavaBase : oitava - 1;
    }

    public int getOitava() {
        return oitava;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getUltimaNota() {
        return ultimaNota;
    }

    public void setUltimaNota(String ultimaNota) {
        this.ultimaNota = ultimaNota;
    }

    public boolean isAnteriorEhNota() {
        return anteriorEhNota;
    }

    public void setAnteriorEhNota(boolean valor) {
        this.anteriorEhNota = valor;
    }

    public void setNumInstrumento(int numInstrumento) {
        this.numInstrumento = numInstrumento;
    }

    public int getNumInstrumento() {
        return numInstrumento;
    }

    public char getUltimaDuracao() {
        return ultimaDuracao;
    }

    public void setUltimaDuracao(char ultimaDuracao) {
        this.ultimaDuracao = ultimaDuracao;
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
    }

    public int getBPM() {
        return bpm;
    }
}
