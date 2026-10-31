package app;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.security.DrbgParameters;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {

    static int CAPACIDAD_TOTAL= 10;
    static int HORA_CIERRE = 22;
    static final int SIN_CELDA = -1;
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
        for (int i= 0; i < CAPACIDAD_TOTAL + 1; i++) {
            registrarEntrada();
        }
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

    static void registrarEntrada() {
        int posicion = celdaDisponible();
        if (posicion == SIN_CELDA) {
            System.out.println("El parqueadero está lleno. No se puede registrar el ingreso.");
            return;
        }

        String tipo = leerTipoVehiculo();
        if (tipo == null) {
            System.out.println("Tipo de vehículo no válido.");
            return;
        }

        LocalDateTime ingreso = leerFechaHora("Fecha y hora de ingreso (yyyy-MM-dd HH:mm): ");

        tipoVehiculo[posicion] = tipo;
        fechaIngreso[posicion] = ingreso;
        celdasOcupadas++;
        celdasLibres--;

        System.out.println("Vehículo registrado en la celda " + (posicion + 1) + ".");
    }


    static int celdaDisponible() {
        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {
            if (tipoVehiculo[i] == null) {
                return i;
            }
        }
        return SIN_CELDA;
    }

    static String leerTipoVehiculo() {
        System.out.print("Tipo de vehículo (1. Carro, 2. Moto): ");
        String texto = SCANNER.nextLine().trim();
        if (texto.equals("1")) {
            return TIPO_CARRO;
        }
        if (texto.equals("2")) {
            return TIPO_MOTO;
        }
        return null;
    }

    static LocalDateTime leerFechaHora(String mensaje) {
        System.out.print(mensaje);
        String texto = SCANNER.nextLine().trim();
        return LocalDateTime.parse(texto, FORMATO_FECHA_HORA);
    }

}

