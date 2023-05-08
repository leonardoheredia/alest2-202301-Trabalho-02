package Grafos;

import java.util.LinkedList;
import java.util.Queue;

public class BuscaEmLargura {
    private static final int MAX_INTEIRO = Integer.MAX_VALUE;
    private boolean visitados[];
    private int verticesAnteriores[];
    private int distancias[];

    public BuscaEmLargura(Grafo grafo, int verticeOrigem) {
        visitados = new boolean[grafo.getNumeroVertices()];
        verticesAnteriores = new int[grafo.getNumeroVertices()];
        distancias = new int[grafo.getNumeroVertices()];
        for (int i = 0; i < distancias.length; i++) distancias[i] = MAX_INTEIRO;
        buscar(grafo, verticeOrigem);
    }
    private void buscar(Grafo grafo, int verticeOrigem) {
        visitados[verticeOrigem] = true;
        distancias[verticeOrigem] = 0;

        Queue<Integer> fila = new LinkedList<>();
        fila.add(verticeOrigem);
        while(!fila.isEmpty()) {
            int verticeAtual = fila.poll();
            for(int verticeVizinho:grafo.adjacentes(verticeAtual)) {
                if(!visitados[verticeVizinho]) {
                    fila.add(verticeVizinho);
                    verticesAnteriores[verticeVizinho] = verticeAtual;
                    distancias[verticeVizinho] = distancias[verticeAtual] + 1;
                    visitados[verticeVizinho] = true;
                }
            }
        }
    }
    public boolean[] getVisitados() {
        return visitados;
    }
    public int[] getVerticesAnteriores() {
        return verticesAnteriores;
    }
    public int[] getDistancias() {
        return distancias;
    }
}
