package Grafos;

import java.util.Scanner;

public class Vista {

    class Input {
        private final Scanner input = new Scanner(System.in);

        public Integer insertarEntero() {
            Integer x = input.nextInt();
            input.close();
            return x;
        }

        public String insertarString() {
            String x = input.nextLine().toUpperCase();
            input.close();
            return x;
        }
    }

    private Gestor gestor;

    Vista() {
        gestor = new Gestor();
    }

    public void init() {
        Input input = new Input();
        String userInput;
        do {
            System.out.println("""
                    1. Insertar vertice.
                    2. Insertar vecino.
                    3. Ver vecinos de un vertice.
                    4. Ver vecinos comunes entre dos vertices.
                    5. Ver grafo.
                    salir = exit = beenden
                    """);
            userInput = input.insertarString();

            switch (userInput) {
                case "1" -> {
                    System.out.print("Valor del vértice: ");
                    this.gestor.insertarVertice(input.insertarEntero());
                }
            }

        } while (!userInput.equals("SALIR") && !userInput.equals("EXIT") && !userInput.equals("BEENDEN"));
    }


}
