package uniquindio.edu.co.ejercicios;

import uniquindio.edu.co.estructuras.Pila;

/**
 * Punto 2 - Obtener un segmento del historial de un editor de texto.
 */
public final class Punto2Historial {

    private Punto2Historial() {
    }

    /**
     * Retorna una pila NUEVA con las acciones de las posiciones inicio..fin (ambas incluidas).
     * Las posiciones se cuentan desde la cima empezando en 1, y la acción de la posición
     * {@code inicio} queda en la cima de la pila resultante.
     *
     * La pila original queda con los mismos elementos en el mismo orden.
     * Si inicio &lt; 1, inicio &gt; fin o fin supera la cantidad de elementos, retorna una pila vacía.
     */
    public static <T> Pila<T> obtenerSegmento(Pila<T> original, int inicio, int fin) {
        Pila<T> resultado = new Pila<>();
        if (inicio < 1 || inicio > fin || fin > original.tamanio()) {
            return resultado;
        }

        Pila<T> auxiliar = new Pila<>();            // guarda todo para restaurar la original
        Pila<T> segmentoInvertido = new Pila<>();   // guarda el segmento al revés

        int posicion = 1;
        while (!original.estaVacia()) {
            T accion = original.desapilar();
            if (posicion >= inicio && posicion <= fin) {
                segmentoInvertido.apilar(accion);
            }
            auxiliar.apilar(accion);
            posicion++;
        }

        // Se restaura la pila original (el orden vuelve a ser el de antes).
        while (!auxiliar.estaVacia()) {
            original.apilar(auxiliar.desapilar());
        }
        // Se invierte el segmento para que la posición "inicio" quede en la cima.
        while (!segmentoInvertido.estaVacia()) {
            resultado.apilar(segmentoInvertido.desapilar());
        }
        return resultado;
    }

    // ------------------------------------------------------------------ demostración

    public static void ejecutar() {
        System.out.println("PUNTO 2 - Obtener un segmento del historial");
        System.out.println("===========================================");

        Pila<String> historial = new Pila<>();
        for (int i = 1; i <= 6; i++) {
            historial.apilar("A" + i);
        }
        System.out.println("Pila original (tope → fondo): " + historial);
        System.out.println();

        int[][] pruebas = {
                {2, 4},   // ejemplo del enunciado → A5 → A4 → A3
                {3, 3},   // inicio = fin → solo A4
                {1, 6},   // toda la pila
                {0, 3},   // inicio < 1 → vacía
                {4, 2},   // inicio > fin → vacía
                {2, 7},   // fin supera la cantidad → vacía
        };
        for (int[] prueba : pruebas) {
            Pila<String> segmento = obtenerSegmento(historial, prueba[0], prueba[1]);
            System.out.println("obtenerSegmento(pila, inicio=" + prueba[0] + ", fin=" + prueba[1] + ")");
            System.out.println("   Resultado (tope → fondo): " + segmento);
            System.out.println("   Original después de la llamada: " + historial);
        }
        System.out.println();
    }
}
