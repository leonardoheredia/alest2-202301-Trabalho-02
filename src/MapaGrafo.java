import Grafos.BuscaEmLargura;
import Grafos.Grafo;
import Grafos.GrafoVertice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class MapaGrafo {
    class Porto {
        public int numero;
        public int numeroVertice;
        public boolean bloqueado;
        public Porto(int numero, int numeroVertice, boolean bloqueado) {
            this.numero = numero;
            this.numeroVertice = numeroVertice;
            this.bloqueado = bloqueado;
        }
    }
    private Grafo grafo;
    private GrafoVertice listaVertices[];
    private HashMap<Integer, GrafoVertice> hashVertices;
    private Mapa mapa;
    private ArrayList<Porto> portos;
    public MapaGrafo(Mapa mapa) {
        this.mapa = mapa;
        grafo = new Grafo(mapa.getColunas() * mapa.getLinhas());
        listaVertices = new GrafoVertice[grafo.getNumeroVertices()];
        hashVertices = new HashMap<>();
        ArrayList<Integer> chaves = new ArrayList<>();
        portos = new ArrayList<>();

        int numeroVertice = 0;
        for (int lin = 0; lin < mapa.getLinhas(); lin++) {
            for (int col = 0; col < mapa.getColunas(); col++) {
                GrafoVertice v = new GrafoVertice(numeroVertice, lin, col, mapa.getMapaDeChar()[lin][col]);
                listaVertices[numeroVertice] = v;
                hashVertices.put(v.getChave(), v);
                chaves.add(v.getChave());
                if (Character.isDigit(v.tipo)) {
                    Porto p = new Porto(Character.getNumericValue(v.tipo), numeroVertice, false);
                    portos.add(p);
                }
                numeroVertice++;
            }
        }
        conectarArestas();
        //System.out.println(grafo.toDot());
    }

    public void conectarArestas() {
        long t1 = System.currentTimeMillis();
        System.out.println("Iniciou conexao de arestas --> " + LocalDateTime.now());
        for (int lin = 0; lin < mapa.getLinhas(); lin++) {
            for (int col = 0; col < mapa.getColunas(); col++) {
                int chave = GrafoVertice.calcularChave(lin, col);
                GrafoVertice v = listaVertices[hashVertices.get(chave).indice];
                if (col + 1 < mapa.getColunas() && v.tipo != '*') {
                    int chavew = GrafoVertice.calcularChave(lin, col+1);
                    GrafoVertice w = listaVertices[hashVertices.get(chavew).indice];
                    if (w.tipo != '*') grafo.adicionarAresta(v.indice, w.indice);
                }
                if (lin + 1 < mapa.getLinhas() && v.tipo != '*') {
                    //GrafoVertice w = listaVertices[getIndiceVertice(lin + 1, col)];
                    int chavew = GrafoVertice.calcularChave(lin+1, col);
                    GrafoVertice w = listaVertices[hashVertices.get(chavew).indice];
                    if (w.tipo != '*') grafo.adicionarAresta(v.indice, w.indice);
                }
            }
            if (lin % 50 == 0) {
                System.out.println(LocalDateTime.now() + " - Finalizou leitura da linha " + lin);
            }
        }
        System.out.println("Terminou conexao de arestas --> " + LocalDateTime.now());

        for (Porto p:portos) {
            int v = p.numeroVertice;
            System.out.println("Grau " + v + " - " + grafo.obterGrau(v));
        }
    }
    public int caminhoMinimoDFS(int verticeOrigem, int verticeDestino) {
        return grafo.caminhoMinimoDFS(verticeOrigem, verticeDestino);
    }
    public int menorCaminhoLargura(int verticeOrigem, int verticeDestino) {
        return grafo.menorCaminhoLargura(verticeOrigem, verticeDestino);
    }
    public int getIndiceVerticePorto(int numeroPorto) {
        for (Porto p : portos) {
            if (p.numero == numeroPorto) return p.numeroVertice;
        }
        return -1;
    }
    public ArrayList<MapaCelula> navegarEmLargura(int verticeOrigem, int verticeDestino) {
        BuscaEmLargura b = new BuscaEmLargura(grafo, verticeOrigem);
        ArrayList<Integer> caminho = new ArrayList<>();
        Stack<Integer> pilha = new Stack<>();
        if (b.getDistancias()[verticeDestino] == Integer.MAX_VALUE) return null;
        for (int verticeAux = verticeDestino; verticeAux != verticeOrigem; verticeAux = b.getVerticesAnteriores()[verticeAux]) {
            pilha.push(verticeAux);
        }
        caminho.add(verticeOrigem);
        while (!pilha.isEmpty()) {
            caminho.add(pilha.pop());
        }

        ArrayList<MapaCelula> m = new ArrayList<>();
        for (int v : caminho) {
            MapaCelula c = new MapaCelula(listaVertices[v].linha, listaVertices[v].coluna);
            m.add(c);
        }
        return m;
    }
}
