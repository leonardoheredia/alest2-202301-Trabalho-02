package grafos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo {
    private ArrayList<Integer> listaAdjacencia[];
    private int numeroVertices;
    private int numeroArestas;
    public Grafo(int numeroVertices) {
        this.numeroVertices = numeroVertices;
        this.numeroArestas = 0;
        listaAdjacencia = new ArrayList[this.numeroVertices];
        for (int i = 0; i < numeroVertices; i++) {
            listaAdjacencia[i] = new ArrayList<>();
        }
    }
    public void adicionarAresta(int v, int w) {
        listaAdjacencia[v].add(w);
        listaAdjacencia[w].add(v);
        numeroArestas++;
    }
    public ArrayList<Integer> adjacentes(int v) {
        return listaAdjacencia[v];
    }
    public int getNumeroVertices() {
        return numeroVertices;
    }
    public int menorCaminhoLargura(int verticeOrigem, int verticeDestino) {
        int[] distancias = new int[this.numeroVertices];
        for (int i = 0; i < numeroVertices; i++) distancias[i] = Integer.MAX_VALUE;
        distancias[verticeOrigem] = 0;
        Queue<Integer> fila = new LinkedList<>();
        fila.add(verticeOrigem);
        while(!fila.isEmpty()) {
            int vertice = fila.poll();
            for (Integer vizinho:adjacentes(vertice)) {
                if(distancias[vizinho]==Integer.MAX_VALUE) {
                    distancias[vizinho] = distancias[vertice] + 1;
                    fila.add(vizinho);
                }
            }
        }
        return distancias[verticeDestino];
    }
    public int obterGrau(int vertice) {
        return adjacentes(vertice).size();
    }
    public String toDot() {
        String resultado = "graph G { " + System.lineSeparator();
        for (int i = 0; i < numeroVertices; i++) {
            resultado = resultado + "\t" + i + ";" + System.lineSeparator();
        }
        for (int i = 0; i < numeroVertices; i++) {
            for (int j = 0; j < listaAdjacencia[i].size(); j++) {
                resultado += "\t" + i + "--" + listaAdjacencia[i].get(j) + ";" + System.lineSeparator();
            }
        }
        resultado += "}";
        return resultado;
    }
}
