package Grafos.WithWeight;

import java.util.*;

public class Graph {
    private final HashMap<Integer, HashMap<Integer, Float>> grafo;

    public Graph() {
        this.grafo = new HashMap<>();
    }

    public boolean insertarVertice(Integer i) {
        //Si no existe el vertice, lo inserto
        if (!grafo.containsKey(i)) {
            this.grafo.put(i, new HashMap<>());
            return true;
        }
        return false;
    }

    public boolean insertarVecino(Integer vertice, Integer verticeVecino, Float peso) {
        //Excepciones: Si no existe el vertice / Si no existe el vertice vecino / Si ya existe la relacion entre vertice y verticeVecino
        if (!this.grafo.containsKey(vertice) || !this.grafo.containsKey(verticeVecino) || this.grafo.get(vertice).containsKey(verticeVecino)) {
            return false;
        }
        this.grafo.get(vertice).put(verticeVecino, peso);
        return true;
    }

    public void printGrafo() {
        this.grafo.forEach((vertice, vecinos) -> System.out.println(vertice + " -> " + vecinos));
        System.out.println();
    }

    public HashMap<Integer, Float> getVecinosDe(Integer vertice) {
        //Excepcion: Si no existe el vertice
        if (!this.grafo.containsKey(vertice)) {
            return null;
        }
        return this.grafo.get(vertice);
    }

    public boolean verVecinosDe(Integer vertice) {
        //Excepcion: Si no existe el vertice
        if (!this.grafo.containsKey(vertice)) {
            return false;
        }
        System.out.print(vertice + " -> ");
        this.grafo.get(vertice).forEach((vecino, peso) -> System.out.print(vecino + "|" + peso + " "));
        System.out.println();
        return true;
    }

    public HashMap<HashMap<Integer, Float>, HashMap<Integer, Float>> getVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        //Excepciones: Si vertice0 no existe / Si vertice1 no existe
        if (!this.grafo.containsKey(vertice0) || !this.grafo.containsKey(vertice1)) {
            return null;
        }
        System.out.println();
        //Excepciones: Si no existen vecinos comunes, retorna []
        HashMap<Integer, Float> tabla0 = this.grafo.get(vertice0);
        HashMap<Integer, Float> tabla1 = this.grafo.get(vertice1);
        Stack<Integer> intersec = new Stack<>();
        HashMap<HashMap<Integer, Float>, HashMap<Integer, Float>> resultado = new HashMap<>();
        for (Integer key : tabla0.keySet()) {
            if (tabla1.containsKey(key)) {
                intersec.push(key);
            }
        }

        return resultado;
    }

    public void verVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        System.out.println(this.getVecinosDe(vertice0));
    }

    @Override
    public String toString() {
        return "Graph{\n" +
                "grafo=" + grafo +
                "\n}";
    }
}
