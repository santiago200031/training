package Grafos.WithWeight;

public class Vista {


    private final Gestor gestor;

    Vista() {
        this.gestor = new Gestor();
        Input input = new Input();
    }

    public void init() {

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
            userInput = Input.insertarString();

            if (userInput != null) {
                switch (userInput) {
                    case "1" -> insertarVertice();
                    case "2" -> insertarVecino();
                    case "3" -> verVecinoVertice();
                    case "4" -> verVecinoComun();
                    case "5" -> this.gestor.printGraph();
                    default -> System.err.println("Valor no válido");
                }
            } else {
                esNull();
                userInput = "";
            }
        } while (!userInput.equals("SALIR") && !userInput.equals("EXIT") && !userInput.equals("BEENDEN"));
        Input.closeBufferedReader();
    }

    private void esNull() {
        System.err.println("Inserte un valor\n");
    }

    private void verVecinoComun() {
        String[] values;
        do {
            System.out.print("Ver vecinos comunes de los vértices (Seperar con espacio): ");
            values = Input.insertarVariosValores();
        } while (values == null || values.length != 2 || values[0] == null || values[1] == null ||
                !this.gestor.verVecinosComunesEntre(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
    }

    private void verVecinoVertice() {
        Integer vertice;
        do {
            System.out.print("Ver vecinos del vértice: ");
            vertice = Input.insertarEntero();
        } while (!this.gestor.verVecinosDe(vertice));
    }

    private void insertarVertice() {
        Integer vertice;
        do {
            System.out.print("Valor del vértice: ");
            vertice = Input.insertarEntero();
        } while (!this.gestor.insertarVertice(vertice));

    }

    private void insertarVecino() {
        String[] values;
        do {
            System.out.print("Insertar vecino del vértice y peso (Seperar con espacio): ");
            values = Input.insertarVariosValores();
        } while (values == null || values.length != 3 || values[0] == null || values[1] == null || values[2] == null ||
                !this.gestor.insertarVecino(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Float.parseFloat(values[2].replace(",", "."))));

    }
}
