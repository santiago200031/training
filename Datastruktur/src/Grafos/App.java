package src.Grafos;

public class App {
    public static void main(String[] args) {
        Graph graph = new Graph();
        if (graph.insertarVertice(1)) {
            System.out.println("Insertado");
        }
        if (graph.insertarVertice(2)) {
            System.out.println("Insertado");
        }
    }
}
