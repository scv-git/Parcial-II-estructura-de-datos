package uniquindio.edu.co.ejercicios;

import uniquindio.edu.co.estructuras.ArbolBinarioBusqueda;
import uniquindio.edu.co.estructuras.NodoBinario;

public class Punto4ArbolEquipos {
    private Punto4ArbolEquipos() {

    }
    public static int contarEquiposEnRango(NodoBinario<Integer> raiz, int limiteInferior, int limiteSuperior) {
        if (raiz == null || limiteInferior > limiteSuperior) {
            return 0;
        }
        int codigo = raiz.getDato();
        if (codigo < limiteInferior) {
            return contarEquiposEnRango(raiz.getDerecho(), limiteInferior, limiteSuperior);
        }
        if (codigo > limiteSuperior) {
            return contarEquiposEnRango(raiz.getIzquierdo(), limiteInferior, limiteSuperior);
        }
        return 1
                + contarEquiposEnRango(raiz.getIzquierdo(), limiteInferior, limiteSuperior)
                + contarEquiposEnRango(raiz.getDerecho(), limiteInferior, limiteSuperior);
    }

    /** Construye el árbol del enunciado. El segundo 60 se ignora porque no se admiten duplicados. */
    public static ArbolBinarioBusqueda<Integer> construirArbol(boolean mostrarMensajes) {
        int[] codigos = {41, 20, 52, 16, 6, 13, 12, 60, 22, 47, 50, 60, 43, 44};
        ArbolBinarioBusqueda<Integer> arbol = new ArbolBinarioBusqueda<>();
        for (int codigo : codigos) {
            boolean insertado = arbol.insertar(codigo);
            if (mostrarMensajes && !insertado) {
                System.out.println("   (el código " + codigo + " ya existe: los códigos son únicos, no se inserta de nuevo)");
            }
        }
        return arbol;
    }
    public static void ejecutar() {
        System.out.println("PUNTO 4 - Árbol binario de búsqueda: consulta por rango y eliminación");
        System.out.println("=====================================================================");

        System.out.println("Inserción en orden: 41, 20, 52, 16, 6, 13, 12, 60, 22, 47, 50, 60, 43, 44");
        ArbolBinarioBusqueda<Integer> arbol = construirArbol(true);
        System.out.println();
        mostrarRecorridos(arbol);

        System.out.println();
        System.out.println("a) contarEquiposEnRango(raiz, inferior, superior)");
        int[][] rangos = {{15, 45}, {44, 52}, {0, 100}, {41, 41}, {53, 59}, {50, 10}};
        for (int[] rango : rangos) {
            int total = contarEquiposEnRango(arbol.getRaiz(), rango[0], rango[1]);
            System.out.println("   Rango [" + rango[0] + ", " + rango[1] + "] → " + total + " equipo(s)");
        }
        System.out.println("   Árbol vacío → " + contarEquiposEnRango(null, 0, 100));
        System.out.println();

        System.out.println("b) Eliminación del nodo 41 (se usa el sucesor inorden: 43)");
        arbol.eliminar(41);
        mostrarRecorridos(arbol);
        System.out.println();
    }

    private static void mostrarRecorridos(ArbolBinarioBusqueda<Integer> arbol) {
        System.out.println("Inorden:   " + arbol.inorden().aTexto(" "));
        System.out.println("Preorden:  " + arbol.preorden().aTexto(" "));
        System.out.println("Postorden: " + arbol.postorden().aTexto(" "));
        System.out.println("Amplitud:  " + arbol.amplitud().aTexto(" "));
    }
}
