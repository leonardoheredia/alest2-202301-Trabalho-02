import java.util.ArrayList;

public class Mapa {

    private int linhas;
    private int colunas;
    private char[][] mapaDeChar;
    private ArrayList<MapaCelula> caminho;
    private ArrayList<ArrayList<MapaCelula>> caminhos;
    public MapaGrafo getMapaGrafo() {
        return mapaGrafo;
    }

    public void setMapaGrafo(MapaGrafo mapaGrafo) {
        this.mapaGrafo = mapaGrafo;
    }
    private MapaGrafo mapaGrafo;
    public Mapa() {
        this.caminho = new ArrayList<>();
        this.caminhos = new ArrayList<>();
    }
    public int getLinhas() {
        return linhas;
    }
    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }
    public int getColunas() {
        return colunas;
    }
    public void setColunas(int colunas) {
        this.colunas = colunas;
    }
    public char[][] getMapaDeChar() {
        return mapaDeChar;
    }
    public void setMapaDeChar(char[][] mapaDeChar) {
        this.mapaDeChar = mapaDeChar;
    }
    public ArrayList<MapaCelula> getCaminho() {
        return caminho;
    }
    public void setCaminho(ArrayList<MapaCelula> caminho) {
        this.caminho = caminho;
    }

    public void setCaminhos(ArrayList<ArrayList<MapaCelula>> caminhos) {
        this.caminhos = caminhos;
    }

    public ArrayList<ArrayList<MapaCelula>> getCaminhos() {
        return caminhos;
    }
}
