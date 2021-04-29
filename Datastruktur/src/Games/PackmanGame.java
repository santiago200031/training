package src.Games;

import java.util.Scanner;

public class PackmanGame {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        char inputUsuario;

        String[][] celdas;
        celdas = rellenarCeros();
        String pacman = "X ";
        String cereza = "O ";
        int puntaie = 0;
        //Empieza operaciones del programa
        int posicionXPacman = (int) (Math.random() * 4);
        int posicionYPacman = (int) (Math.random() * 4);
        int posicionXCereza = (int) (Math.random() * 4);
        int posicionYCereza = (int) (Math.random() * 4);

        while (posicionXCereza == posicionXPacman && posicionYCereza == posicionYPacman) {
            posicionXCereza = (int) (Math.random() * 4);
            posicionYCereza = (int) (Math.random() * 4);
        }


        /*
        0000
        0000
        0000
        0000
         */

        celdas[posicionXCereza][posicionYCereza] = cereza;
        celdas[posicionXPacman][posicionYPacman] = pacman;
        try {


            do {

                verCeldas(celdas);
                inputUsuario = input.nextLine().charAt(0);
                switch (inputUsuario) {
                    case 'd':
                        if (posicionYPacman == 3) {
                            System.out.println("No es posible mover");
                        } else {
                            int posicionAMover = posicionYPacman + 1;
                            celdas[posicionXPacman][posicionYPacman] = "0 ";
                            celdas[posicionXPacman][posicionAMover] = "X ";
                            if (posicionXPacman == posicionXCereza && posicionAMover == posicionYCereza) {
                                puntaie += 5;
                                while (posicionXCereza == posicionXPacman && posicionYCereza == posicionAMover) {
                                    posicionXCereza = (int) (Math.random() * 4);
                                    posicionYCereza = (int) (Math.random() * 4);
                                }
                            }
                            celdas = rellenarCeros();
                            celdas[posicionXCereza][posicionYCereza] = cereza;
                            posicionYPacman = posicionAMover;
                            celdas[posicionXPacman][posicionAMover] = pacman;

                        }

                        break;
                    case 's':
                        if (posicionXPacman == 3) {
                            System.out.println("No es posible mover.");
                        } else {
                            int posicionAMover = posicionXPacman + 1;
                            celdas[posicionXPacman][posicionYPacman] = "0 ";
                            celdas[posicionAMover][posicionYPacman] = "X ";
                            if (posicionXCereza == posicionAMover && posicionYCereza == posicionYPacman) {
                                puntaie += 5;
                                while (posicionXCereza == posicionAMover && posicionYCereza == posicionYPacman) {
                                    posicionXCereza = (int) (Math.random() * 4);
                                    posicionYCereza = (int) (Math.random() * 4);
                                }

                            }
                            celdas = rellenarCeros();
                            celdas[posicionXCereza][posicionYCereza] = cereza;
                            posicionXPacman = posicionAMover;
                            celdas[posicionAMover][posicionYPacman] = pacman;
                        }
                        break;
                    case 'a':
                        if (posicionYPacman == 0) {
                            System.out.println("No es posible mover.");
                        } else {
                            int posicionAMover = posicionYPacman - 1;
                            celdas[posicionXPacman][posicionYPacman] = "0 ";
                            celdas[posicionXPacman][posicionAMover] = "X ";
                            if (posicionXPacman == posicionXCereza && posicionAMover == posicionYCereza) {
                                while (posicionXPacman == posicionXCereza && posicionAMover == posicionYCereza) {
                                    posicionXCereza = (int) (Math.random() * 4);
                                    posicionYCereza = (int) (Math.random() * 4);
                                }
                                puntaie += 5;
                            }
                            celdas = rellenarCeros();
                            celdas[posicionXCereza][posicionYCereza] = cereza;
                            posicionYPacman = posicionAMover;
                            celdas[posicionXPacman][posicionAMover] = pacman;
                        }
                        break;
                    case 'w':
                        if (posicionXPacman == 0) {
                            System.out.println("No es posible mover.");
                        } else {
                            int posicionAMover = posicionXPacman - 1;
                            celdas[posicionXPacman][posicionYPacman] = "0 ";
                            celdas[posicionAMover][posicionYPacman] = "X ";
                            if (posicionAMover == posicionXCereza && posicionYPacman == posicionYCereza) {
                                puntaie += 5;
                                while (posicionAMover == posicionXCereza && posicionYPacman == posicionYCereza) {
                                    posicionXCereza = (int) (Math.random() * 4);
                                    posicionYCereza = (int) (Math.random() * 4);
                                }
                            }
                            celdas = rellenarCeros();
                            celdas[posicionXCereza][posicionYCereza] = cereza;
                            posicionXPacman = posicionAMover;
                            celdas[posicionAMover][posicionYPacman] = pacman;
                        }
                }
            } while (inputUsuario != 'q' && inputUsuario != 'Q');

            System.out.println("Puntaie total:" + puntaie);
        } catch (Exception ignored) {

        }

    }

    private static String[][] rellenarCeros() {
        String[][] celdas = new String[4][4];
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[0].length; j++) {
                celdas[i][j] = "0 ";
            }
        }
        return celdas;
    }


    //public static tipoRetorno
    //int String float char void

    public static void verCeldas(String[][] celdas) {
        for (String[] celda : celdas) {
            for (int i = 0; i < celdas[0].length; i++) {
                System.out.print(celda[i]);
            }
            System.out.println();
        }
    }
}
