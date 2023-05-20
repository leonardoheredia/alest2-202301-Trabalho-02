package grafos;

public class GrafoVertice {
    private int chave;
    public int indice;
    public int linha;
    public int coluna;
    public char tipo;
    public GrafoVertice(int indice, int linha, int coluna, char tipo) {
        this.indice = indice;
        this.linha = linha;
        this.coluna = coluna;
        this.tipo = tipo;
        this.chave = GrafoVertice.calcularChave(this.linha, this.coluna);
    }
    public int getLinha() {
        return linha;
    }
    public int getColuna() {
        return coluna;
    }
    public char getTipo() {
        return tipo;
    }
    public int getChave() {
        return chave;
    }
    public static int calcularChave(int linha, int coluna) {
        return linha * 1_000 + coluna;
    }
}
