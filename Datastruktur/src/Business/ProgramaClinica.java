package Programme.Business;

import java.util.ArrayList;
import java.util.Scanner;

public class ProgramaClinica {

    /*
    Interfaces
     */
    public interface PacientesInterface {
        default float calcularIMC() {
            return 0;
        }

        public default String valorIMC() {
            return null;
        }
    }

    public interface ClinicaInterface {
        public default void imprimirPacientes() {

        }
    }

    /*
    Abstract Classes
     */

    public static abstract class Persona {

        private String nombre;
        private char genero;
        private int edad;
        private float peso, altura;

        public float getPeso() {
            return peso;
        }

        public boolean setPeso(float peso) {
            if (peso > 1) {
                this.peso = peso;
                return true;
            } else {
                return false;
            }
        }

        public String getNombre() {
            return nombre;
        }

        public boolean setNombre(String nombre) {
            if (nombre.length() > 2) {
                this.nombre = nombre.toUpperCase();
                return true;
            }
            System.out.println("El nombre debe de tener al menos 3 letras.");
            return false;
        }

        public char getGenero() {
            return genero;
        }

        public boolean setGenero(String generoPaciente) {
            //Si se ingresa M o F, se tiene lenght==1
            if (generoPaciente != null && generoPaciente.length() == 1) {
                char genero = generoPaciente.toUpperCase().charAt(0);
                if (genero == 'M' || genero == 'F') {
                    this.genero = genero;
                    return true;
                }
            }
            System.out.println("Ingresar M o F.");
            return false;
        }

        public int getEdad() {
            return edad;
        }

        public boolean setEdad(int edad) {
            if (edad > 0) {
                this.edad = edad;
                return true;
            }
            System.out.println("No se puede ingresar valores negativos.");
            return false;
        }

        public boolean setAltura(float altura) {
            if (altura > 5) {
                this.altura = altura;
                return true;
            }
            System.out.println("No se puede ingresar valores menores a 5cm.");
            return false;
        }

        public float getAltura() {
            return altura;
        }
    }

    /*
    Classes
     */

    public static class Paciente extends Persona implements PacientesInterface {

        Paciente() {
            super();
        }

        @Override
        public float calcularIMC() {
            //Conversion de lb a Kg
            //1lb = 0.453592Kg
            //En ingles BMI
            return (float) (this.getPeso() * 0.45 / Math.pow(this.getAltura(), 2)) * 10000;
        }

        @Override
        public String valorIMC() {
            float imc = calcularIMC();
            switch (this.getGenero()) {
                case 'F':
                    if (this.getEdad() < 1) {
                        if (imc < 14) {
                            return "Bajo de Peso";
                        } else if (imc > 14 && imc <= 17) {
                            return "Peso Normal";
                        } else if (imc > 17) {
                            return "Sobre peso";
                        } else {
                            return "Error al calcular IMC!";
                        }
                    } else if (this.getEdad() > 1 && this.getEdad() <= 12) {
                        if (imc < 40) {
                            return "Bajo de Peso";
                        } else if (imc > 40 && imc <= 55) {
                            return "Peso Normal";
                        } else if (imc > 55) {
                            return "Sobre peso";
                        } else {
                            return "Error al calcular IMC!";
                        }
                    } else if (this.getEdad() > 12) {
                        if (imc < 18.5) {
                            return "Bajo de Peso";
                        } else if (imc > 18.5 && imc <= 24.9) {
                            return "Peso Normal";
                        } else if (imc > 24.9) {
                            return "Sobre peso";
                        } else {
                            return "Error al calcular IMC!";
                        }
                    }
                    break;
                case 'M':
                    if (this.getEdad() < 1) {
                        if (imc < 15) {
                            return "Bajo de Peso";
                        } else if (imc > 15 && imc <= 19) {
                            return "Peso Normal";
                        } else if (imc > 19) {
                            return "Sobre peso";
                        } else {
                            return "Error al calcular IMC!";
                        }
                    } else if (this.getEdad() > 1 && this.getEdad() <= 12) {
                        if (imc < 42) {
                            return "Bajo de Peso";
                        } else if (imc > 42 && imc <= 56) {
                            return "Peso Normal";
                        } else if (imc > 56) {
                            return "Sobre peso";
                        } else {
                            return "Error al calcular IMC!";
                        }
                    } else if (this.getEdad() > 12) {
                        if (imc < 19.7) {
                            return "Bajo de Peso";
                        } else if (imc > 19.7 && imc <= 25.4) {
                            return "Peso Normal";
                        } else if (imc > 25.4) {
                            return "Sobre peso";
                        } else {
                            return "Error al calcular IMC!";
                        }
                    }
                    break;
            }
            return "No se calculó el IMC!";

        }

        @Override
        public String toString() {
            return "Nombre: " + this.getNombre() + "  Edad: " + this.getEdad() + "  Genero: " + this.getGenero() + "\n" +
                    "  IMC: " + this.calcularIMC() + "  Dato: " + this.valorIMC() + "\n";
        }
    }

    public static class Clinica implements ClinicaInterface {
        private String nombre;
        private int cantidadPacientes;
        private final ArrayList<Paciente> pacientes = new ArrayList<>();

        public boolean insertarPaciente(Paciente p) {
            if (this.cantidadPacientes > 0) {
                this.pacientes.add(p);
                cantidadPacientes--;
                return true;
            }
            System.out.print("Ya no se puede atender a mas Pacientes!");
            return false;
        }

        @Override
        public void imprimirPacientes() {
            for (Paciente p : this.pacientes) {
                System.out.println(p.toString());
            }
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getCantidadPacientes() {
            return cantidadPacientes;
        }

        public void setCantidadPacientes(int cantidadPacientes) {
            this.cantidadPacientes = cantidadPacientes;
        }
    }

    /*
    Main
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreClinica, nombrePaciente;
        int cantidadPacientes, edadPaciente;
        float pesoPaciente, alturaPaciente;
        String generoPaciente;

        //Crear clinica
        Clinica clinica = new Clinica();
        System.out.print("Nombre de la Clinica: ");
        nombreClinica = sc.nextLine();
        clinica.setNombre(nombreClinica);
        System.out.print("Cantidad de pacientes a antender: ");
        cantidadPacientes = Integer.parseInt(sc.next());
        clinica.setCantidadPacientes(cantidadPacientes);
        System.out.println();
        //

        //Mensaje Bienvenida
        System.out.println("*** Bienvenido a la Clinica " + clinica.getNombre() + " ***");
        System.out.println("Clientes a atender hoy: " + clinica.getCantidadPacientes());
        System.out.println();
        //
        String valorUsuario;
        do {
            //Menu
            System.out.println("""
                    1. Insertar nuevo cliente.
                    2. Mostrar Pacientes.
                    Si desea salir, digite: "salir\"""");
            valorUsuario = sc.next();
            System.out.println();
            //
            //Creamos Paciente
            //
            switch (valorUsuario.toUpperCase()) {
                case "1" -> {
                    if (clinica.getCantidadPacientes() > 0) {
                        Paciente paciente = new Paciente();
                        do {
                            System.out.print("Nombre: ");
                            nombrePaciente = sc.next();
                        } while (!paciente.setNombre(nombrePaciente));
                        do {
                            System.out.print("Edad: ");
                            edadPaciente = Integer.parseInt(sc.next());
                        } while (!paciente.setEdad(edadPaciente));
                        do {
                            System.out.print("Peso (lb.): ");
                            pesoPaciente = sc.nextInt();
                        } while (!paciente.setPeso(pesoPaciente));
                        do {
                            System.out.print("Altura (cm): ");
                            alturaPaciente = Float.parseFloat(sc.next());

                        } while (!paciente.setAltura(alturaPaciente));
                        do {
                            System.out.print("Genero (M / F): ");
                            generoPaciente = sc.next().toUpperCase();
                        } while (!paciente.setGenero(generoPaciente));
                        do {
                            System.out.println("\nInsertando paciente...\n");
                        } while (!clinica.insertarPaciente(paciente));
                        System.out.println(paciente);
                    } else {
                        System.out.println("No se puede agregar más pacientes!");
                    }
                }
                case "2" -> clinica.imprimirPacientes();

                default -> System.out.println("Opción no válida.");
            }
        } while (!valorUsuario.equals("SALIR"));
        System.out.println("Adios!");
    }
}