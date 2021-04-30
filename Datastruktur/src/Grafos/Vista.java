package Grafos;

import java.util.Scanner;

public class Vista {

    class Input {
        private Scanner input = new Scanner(System.in);

        public Integer insertarEntero() {
            return input.nextInt();
        }

        public String insertarString() {
            return input.nextLine().toUpperCase();
        }

        public void closeScanner() {
            this.input.close();
        }
    }

    private final Gestor gestor;

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
                case "2" -> {
                    System.out.print("Insertar vecino del vértice (Seperar con espacio): ");
                    String[] values = input.insertarString().split(" ");
                    if (values.length != 2) {
                        break;
                    }
                    this.gestor.insertarVecino(input.insertarEntero(), input.insertarEntero());
                }
                case "3" -> {
                    System.out.print("Ver vecino del vértice: ");
                    this.gestor.getVecinosDe(input.insertarEntero());
                }
                case "4" -> {
                    System.out.print("Ver vecinos comunes de los vértices (Seperar con espacio): ");
                    String[] values = input.insertarString().split(" ");
                    if (values.length != 2) {
                        break;
                    }
                    this.gestor.getVecinosComunesEntre(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                }
                case "5" -> {
                    this.gestor.printgraph();
                }
            }
        } while (!userInput.equals("SALIR") && !userInput.equals("EXIT") && !userInput.equals("BEENDEN"));
        input.closeScanner();
    }


}
