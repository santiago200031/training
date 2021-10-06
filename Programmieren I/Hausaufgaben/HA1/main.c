#include <stdio.h>

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
        i++;
    }
    printf("\n");
}

void aufgabe3() {

}

void aufgabe4() {

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



