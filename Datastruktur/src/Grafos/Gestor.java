package Grafos;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Gestor {
    private final Graph graph;

    Gestor() {
        graph = new Graph();
    }

    public void insertarVertice(Integer i) {
        System.out.println("Insertando...\n");
        if (graph.insertarVertice(i)) {
            System.out.println("Vertice insertado: " + i);
        } else {
            System.out.println("Fallo al insertar vertice " + i);
        }
    }

    public void insertarVecino(Integer vertice, Integer verticeVecino) {
        System.out.println("Insertando...\n");
        if (graph.insertarVecino(vertice, verticeVecino)) {
            System.out.println("Vertice: " + vertice + "\tVertice Vecino: " + verticeVecino);
        } else {
            System.out.println("Fallo al insertar el vecino " + verticeVecino + " del vertice " + vertice);
        }
    }

    public void printgraph() {
        System.out.println("Imprimiendo grafo...\n");
        this.graph.printGrafo();
    }

    public ArrayList<Integer> getVecinosDe(Integer vertice) {
        return this.graph.getVecinosDe(vertice);
    }

    public void verVecinosDe(Integer vertice) {
        System.out.println("Imprimiendo vecinos de: " + vertice + "\n");
        if (!this.graph.verVecinosDe(vertice)) {
            System.out.println("Error al imprimir los vecinos de: " + vertice);
        }
    }

    public ArrayList<Integer> getVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        return this.graph.getVecinosComunesEntre(vertice0, vertice1);
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "graph=" + graph +
                '}';
    }


}
