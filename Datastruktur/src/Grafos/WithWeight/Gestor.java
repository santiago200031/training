package Grafos.WithWeight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

class Input {
    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static Integer insertarEntero() {
        try {
            String line = input.readLine();
            if (!line.equals("")) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String insertarString() {
        try {
            String line = input.readLine();
            if (!line.equals("")) {
                return line.toUpperCase();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] insertarVariosValores() {
        try {
            String line = input.readLine();
            if (!line.equals("")) {
                return line.split(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeBufferedReader() {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Gestor {
    private final Graph graph;

    Gestor() {
        this.graph = new Graph();
    }

    public boolean insertarVertice(Integer i) {
        if (i != null) {
            System.out.println("Insertando...\n");
            if (this.graph.insertarVertice(i)) {
                System.out.println("Vertice insertado: " + i + "\n");
                return true;
            }
            System.err.println("Fallo al insertar vertice " + i);
        }
        System.err.println("Insertar un valor.");
        return false;
    }

    public boolean insertarVecino(Integer vertice, Integer verticeVecino, Float peso) {
        if (vertice != null && verticeVecino != null) {
            System.out.println("Insertando...\n");
            if (this.graph.insertarVecino(vertice, verticeVecino, peso)) {
                System.out.println("Vertice: " + vertice + "\tVertice Vecino: " + verticeVecino + "\t Con el Peso de: " + peso + "\n");
                return true;
            }
            System.err.println("Fallo al insertar el vecino " + verticeVecino + " del vertice " + vertice);
            return false;
        }
        System.err.println("Insertar un valor");
        return false;
    }

    public void printGraph() {
        System.out.println("Imprimiendo grafo...\n");
        this.graph.printGrafo();
    }

    public HashMap<Integer, Float> getVecinosDe(Integer vertice) {
        if (vertice != null) {
            return this.graph.getVecinosDe(vertice);
        }
        return null;
    }

    public boolean verVecinosDe(Integer vertice) {
        if (vertice != null) {
            System.out.println(this.getVecinosDe(vertice));
            return true;
        }
        return false;
    }

    public HashMap<HashMap<Integer, Float>, HashMap<Integer, Float>> getVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        if (vertice0 != null && vertice1 != null) {
            return this.graph.getVecinosComunesEntre(vertice0, vertice1);
        }
        return null;
    }

    public boolean verVecinosComunesEntre(Integer vertice0, Integer vertice1) {
        if (vertice0 != null && vertice1 != null) {
            System.out.println(this.graph.getVecinosComunesEntre(vertice0, vertice1));
            return true;
        }
        System.err.println("Insertar valores");
        return false;
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "graph=" + this.graph +
                '}';
    }


}
