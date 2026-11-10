package uniquindio.edu.co.ejercicios;

/**
 * Guía del punto 5. El AVL y el Rojo-Negro se construyen manualmente,
 * como indica el enunciado del parcial.
 */
public final class Punto5Arboles {

    public static final String[] PALABRAS = {
            "alma", "beso", "azul", "cine", "eco", "dedo", "bota", "hilo", "lago", "isla",
            "nube", "pato", "oro", "mapa", "gato"
    };

    private Punto5Arboles() {
    }

    public static void ejecutar() {
        System.out.println("PUNTO 5 - AVL y Árbol Rojo-Negro");
        System.out.println("================================");
        System.out.println("Palabras insertadas en el AVL:");
        for (String palabra : PALABRAS) {
            System.out.print(palabra + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Postorden obtenido del procedimiento manual después de eliminar cine:");
        System.out.println("alma, beso, azul, dedo, gato, hilo, eco, bota, mapa, lago, oro, pato, nube, isla");
        System.out.println();
        System.out.println("Los pasos, FE, rotaciones y el árbol Rojo-Negro se entregan dibujados a mano.");
        System.out.println("La guía está en docs/RESPUESTAS.md.");
        System.out.println();
    }
}
