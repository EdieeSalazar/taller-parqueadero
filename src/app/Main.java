package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {

    static int CAPACIDAD_TOTAL= 10;
    static int HORA_CIERRE = 22;
    static int HORA_APERTURA = 8;
    static int UMBRAL_HORAS_DIA = 8;
    static int TARIFA_HORA_CARRO = 4000;
    static int TARIFA_DIA_CARRO = 30000;
    static int TARIFA_HORA_MOTO= 2000;
    static int TARIFA_DIA_MOTO = 15000;

    static final String TIPO_CARRO = "CARRO";
    static final String TIPO_MOTO = "MOTO";

    static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    static final Scanner SCANNER = new Scanner(System.in);

    static int celdasOcupadas;
    static int celdasLibres;
    static long recaudoCarros;
    static long recaudoMotos;

    static String[] tipoVehiculo = new String[CAPACIDAD_TOTAL];
    static LocalDateTime[] fechaIngreso = new LocalDateTime[CAPACIDAD_TOTAL];


    public static void main(String[] args) {
        inicializarArreglos();
        System.out.println("     SISTEMA DE PARQUEADERO");
        mostrarMenu();
        System.out.println("Menú en construcción.");
        SCANNER.close();
    }

    static void inicializarArreglos() {
        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {
            tipoVehiculo[i] = null;
            fechaIngreso[i] = null;
        }
        celdasOcupadas = 0;
        celdasLibres = CAPACIDAD_TOTAL;
        recaudoCarros = 0;
        recaudoMotos = 0;
    }

    static void mostrarMenu() {
        System.out.println("");
        System.out.println("1. Registrar entrada");
        System.out.println("2. Registrar salida");
        System.out.println("3. Reporte de ocupación");
        System.out.println("4. Reporte financiero");
        System.out.println("5. Salir");
    }

    static void imprimir(String mensaje) {
        System.out.println(mensaje);
    }

}

