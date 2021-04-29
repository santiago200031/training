package src.Grafos;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<Integer, ArrayList<Integer>> grafo;

    Graph() {
        this.grafo = new HashMap<>();
    }

    public boolean insertarVertice(Integer i) {
        if (!grafo.containsKey(i)) {
            this.grafo.put(i, new ArrayList<>());
            return true;
        }
        return false;
    }

    public boolean insertarVecino(Integer vertice, Integer verticeVecino) {
        if (!this.grafo.containsKey(vertice) || !this.grafo.containsKey(verticeVecino)) {
            return false;
        }
        this.grafo.get(vertice).add(verticeVecino);
        return true;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertice=" + grafo +
                '}';
    }
}
