package uniquindio.edu.co;

import uniquindio.edu.co.ejercicios.Punto1Cajas;
import uniquindio.edu.co.ejercicios.Punto2Historial;
import uniquindio.edu.co.ejercicios.Punto3Paquetes;
import uniquindio.edu.co.ejercicios.Punto4ArbolEquipos;
import uniquindio.edu.co.ejercicios.Punto5Arboles;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Parcial II - Estructuras de Datos (Universidad del Quindío).
 *
 * Uso:
 *   sin argumentos → ejecuta los 5 puntos
 *   argumento 1..5 → ejecuta solo ese punto (por ejemplo: java ... Main 3)
 */
public class Main {

    public static void main(String[] args) {
        // Para que tildes, flechas y los dibujos de los árboles se vean bien en cualquier consola.
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        String opcion = args.length > 0 ? args[0].trim() : "todos";
        switch (opcion) {
            case "1":
                Punto1Cajas.ejecutar();
                break;
            case "2":
                Punto2Historial.ejecutar();
                break;
            case "3":
                Punto3Paquetes.ejecutar();
                break;
            case "4":
                Punto4ArbolEquipos.ejecutar();
                break;
            case "5":
                Punto5Arboles.ejecutar();
                break;
            default:
                Punto1Cajas.ejecutar();
                Punto2Historial.ejecutar();
                Punto3Paquetes.ejecutar();
                Punto4ArbolEquipos.ejecutar();
                Punto5Arboles.ejecutar();
        }
    }
}
