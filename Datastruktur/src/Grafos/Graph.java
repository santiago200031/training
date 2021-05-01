package Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Graph {
    private final HashMap<Integer, ArrayList<Integer>> grafo;

    public Graph() {
        this.grafo = new HashMap<>();
    }

    public boolean insertarVertice(Integer i) {
        //Si no existe el vertice, lo inserto
        if (!grafo.containsKey(i)) {
            this.grafo.put(i, new ArrayList<>());
            return true;
        }
        return false;
    }

    public boolean insertarVecino(Integer vertice, Integer verticeVecino) {
        //Excepciones: Si no existe el vertice / Si no existe el vertice vecino / Si ya existe la relacion entre vertice y verticeVecino
        if (!this.grafo.containsKey(vertice) || !this.grafo.containsKey(verticeVecino) || this.grafo.get(vertice).contains(verticeVecino)) {
            return false;
        }
        this.grafo.get(vertice).add(verticeVecino);
        return true;
    }

    public void printGrafo() {
        this.grafo.forEach((vertice, vecinos) -> System.out.println(vertice + " -> " + vecinos));
        System.out.println();
    }

    public ArrayList<Integer> getVecinosDe(Integer vertice) {
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
        this.grafo.get(vertice).forEach(vecino -> System.out.print(vecino + " "));
        System.out.println();
        return true;
    }

    public ArrayList<Integer> getVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        //Excepciones: Si vertice0 no existe / Si vertice1 no existe
        if (!this.grafo.containsKey(vertice0) || !this.grafo.containsKey(vertice1)) {
            return null;
        }
        System.out.println();
        //Excepciones: Si no existen vecinos comunes, retorna []
        return (ArrayList<Integer>) this.getVecinosDe(vertice0).parallelStream().filter(this.getVecinosDe(vertice1)::contains).collect(Collectors.toList());
    }

    public void verVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        System.out.println(this.getVecinosDe(vertice0).parallelStream().filter(this.getVecinosDe(vertice1)::contains).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "Graph{\n" +
                "grafo=" + grafo +
                "\n}";
    }
}
