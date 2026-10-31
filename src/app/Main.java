package app;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.security.DrbgParameters;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

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
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1 -> registrarEntrada();
                case 2, 3, 4 -> System.out.println("Esta opción se habilita en un commit posterior.");
                case 5 -> System.out.println("Hasta luego.");
            }
        } while (opcion != 5);
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

    static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException e) {

            }
            System.out.println("Dato inválido. Ingrese un número entre " + minimo + " y " + maximo + ".");
        }
    }

    static int leerOpcion() {
        return leerEntero("Seleccione una opción: ", 1, 5);
    }

    static String leerTipoVehiculo() {
        int tipo = leerEntero("Tipo de vehículo (1. Carro, 2. Moto): ", 1, 2);
        return tipo == 1 ? TIPO_CARRO : TIPO_MOTO;
    }

    static int leerPosicion() {
        return leerEntero("Número de celda (1 a " + CAPACIDAD_TOTAL + "): ", 1, CAPACIDAD_TOTAL);
    }

    static LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();
            if (!formatoFechaHoraValido(texto)) {
                imprimir("Fecha u hora inválidas. Use yyyy-MM-dd HH:mm y el horario "
                        + HORA_APERTURA + ":00-" + HORA_CIERRE + ":00.");
                continue;
            }

            LocalDateTime fecha;
            try {
                fecha = LocalDateTime.parse(texto, FORMATO_FECHA_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha u hora inválidas. Use yyyy-MM-dd HH:mm.");
                continue;
            }


            if (!fecha.format(FORMATO_FECHA_HORA).equals(texto)) {
                System.out.println("La fecha no existe en el calendario.");
                continue;
            }

            return fecha;
        }
    }

    static boolean formatoFechaHoraValido(String texto) {
        if (texto.length() != 16
                || texto.charAt(4) != '-'
                || texto.charAt(7) != '-'
                || texto.charAt(10) != ' '
                || texto.charAt(13) != ':') {
            return false;
        }

        int anio = leerDigitos(texto, 0, 4);
        int mes = leerDigitos(texto, 5, 2);
        int dia = leerDigitos(texto, 8, 2);
        int hora = leerDigitos(texto, 11, 2);
        int minuto = leerDigitos(texto, 14, 2);

        return anio >= 1 && mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31
                && hora >= HORA_APERTURA && hora <= HORA_CIERRE
                && (hora != HORA_CIERRE || minuto <= 0)
                && minuto >= 0 && minuto <= 59;
    }

    static int leerDigitos(String texto, int inicio, int cantidad) {
        int valor = 0;
        for (int i = inicio; i < inicio + cantidad; i++) {
            char caracter = texto.charAt(i);
            if (caracter < '0' || caracter > '9') {
                return -1;
            }
            valor = valor * 10 + caracter - '0';
        }
        return valor;
    }
}

