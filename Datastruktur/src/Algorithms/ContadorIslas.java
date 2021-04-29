package src.Algorithms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ContadorIslas {

    public static class Grid {

        private final Integer[][] celdas;

        Grid(int xy) {
            this.celdas = new Integer[xy][xy];
        }

        Grid(int x, int y) {
            this.celdas = new Integer[x][y];
        }

        public void llenarConCeros() {
            for (int i = 0; i < this.celdas.length; i++) {
                for (int j = 0; j < this.celdas[0].length; j++) {
                    this.celdas[i][j] = 0;
                }
            }
        }

        public void printGrid() {
            for (Integer[] i : this.celdas) {
                System.out.println(Arrays.toString(i));
            }
        }

        public void createIslands() {
            int numberOfTimes = 8;
            for (Integer[] i : this.celdas) {
                for (int j = 0; j < numberOfTimes; j++) {
                    i[(int) (Math.random() * this.celdas.length)] = 1;
                }
            }
        }

        public void printLine() {
            for (int i = 0; i < this.celdas[0].length; i++) {
                System.out.print("---");
            }
            System.out.println();
        }

        public void verNumeroIslas() {
            int n = this.celdas.length;
            int m = this.celdas[0].length;
            int ni = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (this.celdas[i][j] == 1) {
                        ni++;
                        puntosVisitados(this.celdas, i, j);
                    }
                }
            }
            System.out.println(ni);
        }

        private void puntosVisitados(Integer[][] celdas, int i, int j) {
            int n = this.celdas.length;
            int m = this.celdas[0].length;
            int valorEnCuadro = celdas[i][j];
            if (valorEnCuadro == 0) {
                return;
            }
            Queue<Integer> queue = new LinkedList<>();
            queue.add(i);
            queue.add(j);
            while (!queue.isEmpty()) {
                i = queue.poll();
                j = queue.poll();
                if (i >= 0 && i < n && j >= 0 && j < m && celdas[i][j] == valorEnCuadro) {
                    celdas[i][j] = 2;
                    queue.add(i + 1);
                    queue.add(j);
                    queue.add(i - 1);
                    queue.add(j);
                    queue.add(i);
                    queue.add(j + 1);
                    queue.add(i);
                    queue.add(j - 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Grid cuadrado = new Grid(10);
        cuadrado.llenarConCeros();
        cuadrado.printGrid();
        cuadrado.createIslands();
        cuadrado.printLine();
        cuadrado.printGrid();
        cuadrado.printLine();
        cuadrado.verNumeroIslas();
        cuadrado.printLine();
        cuadrado.printGrid();

        Grid rectangulo = new Grid(10, 20);
        rectangulo.llenarConCeros();
        rectangulo.printGrid();
        rectangulo.createIslands();
        rectangulo.printLine();
        rectangulo.printGrid();
        rectangulo.printLine();
        rectangulo.verNumeroIslas();
        rectangulo.printLine();
        rectangulo.printGrid();
    }
}
