#include <stdio.h>

#define PI 3.1415f

void aufgabe1() {
    printf("*** Hello World! - Hola mundo! - Hallo Welt! ***\n\n");
}


void aufgabe2() {
    size_t typesGrosse[8] = {sizeof(char), sizeof(short), sizeof(int), sizeof(float),
                             sizeof(double), sizeof(long double), sizeof(signed), sizeof(unsigned)};
    char *types[8] = {"Grosse von Char = ", "Grosse von Short = ",
                      "Grosse von Integer = ", "Grosse von Float = ",
                      "Grosse von Double = ", "Grosse von Long Double = ",
                      "Grosse von Signed = ", "Grosse von Unsigned = "};
    int i = 0;
    while (i < 8) {
        printf("%s %s %s %zu, %s", "Grosse von ", types[i], " ", typesGrosse[i], " Bytes\n");
        ++i;
    }
    printf("\n");
}

void aufgabe3() {
    float durchmesser = 0.7f; /* in Meter */
    float drehzahl1 = 10; /* in rad/s */
    float drehzahl2 = 8; /* in Umdrehungen/s */
    float geschwindigkeitRad1MS = (2 * PI * durchmesser) / drehzahl1; /* in m/s */
    float geschwindigkeitRad2MS = (2 * PI * durchmesser) / drehzahl2;;
    float geschwindigkeitsauto = (geschwindigkeitRad1MS + geschwindigkeitRad2MS) / 2;
    printf("%s %.2f %s %.2f %s %.2f %s", "Durchmesser: ", durchmesser, "\nDrehzal 1: ", drehzahl1, "\nDrehzal 2: ",
           drehzahl2, "\n");
    printf("%s %.2f %s", "Geschwindigkeitsfahrzeuge: ", geschwindigkeitsauto, "\n\n");
}

void printTabelle(float firstArray[], float secondArray[], int size) {
    for (int i = 0; i < size; ++i) {
        printf("%.2f %10.2f %s", firstArray[i], secondArray[i], "\n");
    }
    printf("%s", "\n");
}

void aufgabe4() {
    float werte[7] = {1, 2, 5, 10, 20, 50, 100};
    float gerechneteWerteUSD[7];
    float gerechneteWerteEUR[7];
    int i = 0;
    while (i < 7) {
        gerechneteWerteEUR[i] = werte[i] * 0.78f;
        gerechneteWerteUSD[i] = werte[i] * 1.28f;
        ++i;
    }
    printf("%s %10s", "EUR", "USD\n");
    printTabelle(werte, gerechneteWerteUSD, 7);
    printf("%s %10s", "USD", "EUR\n");
    printTabelle(werte, gerechneteWerteEUR, 7);
}

int main() {
    int input;
    do {
        printf("%s",
               "1. Aufgabe (\"Hello World - Hola mundo - Hallo Welt\")\n"
               "2. Aufgabe (\"Datentypengrossen\")\n"
               "3. Aufgabe (\"Berechnung der Fahrzeuggeschwindigkeit\")\n"
               "4. Aufgabe (\"Wahrungsumrechnung\")\n"
               "5. Close\n"
        );
        printf("%s", "Value: ");
        scanf("%d", &input);
        switch (input) {
            case 1:
                aufgabe1();
                break;
            case 2:
                aufgabe2();
                break;
            case 3:
                aufgabe3();
                break;
            case 4:
                aufgabe4();
                break;
            case 5:
                printf("Auf Wiedersehen / Hasta luego");
                break;
            default:
                printf("%s", "Aufgabe existiert leider nicht!\n");
        }
    } while (input != 5);
    return 0;
}



