package uniquindio.edu.co.ejercicios;

import uniquindio.edu.co.estructuras.Cola;
import uniquindio.edu.co.model.Paquete;
import uniquindio.edu.co.model.TipoEntrega;

public class Punto3Paquetes {
    private Punto3Paquetes(){

    }
    public static Cola<Paquete> seleccionarPaquetes(Cola<Paquete> pendientes, int capacidad){
        return seleccionarPaquetes(pendientes, capacidad, false);
    }
    public static Cola<Paquete> seleccionarPaquetes(Cola<Paquete> pendientes, int capacidad, boolean mostrarTraza) {
        Cola<Paquete> seleccionados = new Cola<>();
        if (capacidad <= 0) {
            return seleccionados;
        }
        if (mostrarTraza) {
            System.out.println("Evaluación de paquetes URGENTES (en orden de registro):");
        }
        int capacidadRestante = procesar(pendientes, seleccionados, TipoEntrega.URGENTE,
                pendientes.tamanio(), capacidad, mostrarTraza);
        if (mostrarTraza) {
            System.out.println("Evaluación de paquetes NORMALES (en orden de registro):");
        }
        procesar(pendientes, seleccionados, TipoEntrega.NORMAL,
                pendientes.tamanio(), capacidadRestante, mostrarTraza);
        return seleccionados;
    }

    private static int procesar(Cola<Paquete> pendientes, Cola<Paquete> seleccionados, TipoEntrega tipo,
                                int n, int capacidad, boolean mostrarTraza) {
        if (n == 0) {
            return capacidad; // caso base: ya se recorrieron todos
        }
        Paquete paquete = pendientes.desencolar();

        if (paquete.getTipo() != tipo) {
            pendientes.encolar(paquete);
            return procesar(pendientes, seleccionados, tipo, n - 1, capacidad, mostrarTraza);
        }
        if (paquete.getPeso() <= capacidad) {
            seleccionados.encolar(paquete);
            int restante = capacidad - paquete.getPeso();
            if (mostrarTraza) {
                System.out.println("   " + paquete + " → " + paquete.getPeso() + " ≤ " + capacidad
                        + " → SELECCIONADO (capacidad restante: " + restante + " kg)");
            }
            return procesar(pendientes, seleccionados, tipo, n - 1, restante, mostrarTraza);
        }
        pendientes.encolar(paquete);
        if (mostrarTraza) {
            System.out.println("   " + paquete + " → " + paquete.getPeso() + " > " + capacidad
                    + " → NO CABE, queda PENDIENTE (capacidad restante: " + capacidad + " kg)");
        }
        return procesar(pendientes, seleccionados, tipo, n - 1, capacidad, mostrarTraza);
    }
    public static int pesoTotal(Cola<Paquete> cola) {
        return pesoTotal(cola, cola.tamanio());
    }

    private static int pesoTotal(Cola<Paquete> cola, int n) {
        if (n == 0) {
            return 0;
        }
        Paquete paquete = cola.desencolar();
        cola.encolar(paquete);
        return paquete.getPeso() + pesoTotal(cola, n - 1);
    }
    public static void ejecutar() {
        System.out.println("PUNTO 3 - Selección de solicitudes para una jornada");
        System.out.println("===================================================");

        Cola<Paquete> pendientes = new Cola<>();
        pendientes.encolar(new Paquete("P1", TipoEntrega.NORMAL, 4));
        pendientes.encolar(new Paquete("P2", TipoEntrega.URGENTE, 7));
        pendientes.encolar(new Paquete("P3", TipoEntrega.NORMAL, 3));
        pendientes.encolar(new Paquete("P4", TipoEntrega.URGENTE, 5));
        pendientes.encolar(new Paquete("P5", TipoEntrega.NORMAL, 6));
        pendientes.encolar(new Paquete("P6", TipoEntrega.URGENTE, 4));
        pendientes.encolar(new Paquete("P7", TipoEntrega.NORMAL, 2));

        int capacidad = 14;
        System.out.println("Cola inicial (frente → final): " + pendientes);
        System.out.println("Capacidad disponible del vehículo: " + capacidad + " kg");
        System.out.println();

        Cola<Paquete> seleccionados = seleccionarPaquetes(pendientes, capacidad, true);
        int capacidadRestante = capacidad - pesoTotal(seleccionados);

        System.out.println();
        System.out.println("Cola retornada (seleccionados, frente → final): " + seleccionados);
        System.out.println("Cola de pendientes después de la selección:     " + pendientes);
        System.out.println("Capacidad que quedó disponible: " + capacidadRestante + " kg");
        System.out.println();

        // Caso límite: capacidad <= 0 → cola vacía y pendientes sin cambios.
        Cola<Paquete> ninguno = seleccionarPaquetes(pendientes, 0);
        System.out.println("Caso límite, capacidad = 0 → cola retornada: " + ninguno
                + " | pendientes sin cambios: " + pendientes);
        System.out.println();
    }
}
