package uniquindio.edu.co.estructuras;

/**
 * Árbol binario de búsqueda para el punto 4 del parcial.
 * No admite claves repetidas.
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> {

    private NodoBinario<T> raiz;

    public NodoBinario<T> getRaiz() {
        return raiz;
    }

    /** Inserta el dato y retorna false si ya existe. */
    public boolean insertar(T dato) {
        if (existe(dato)) {
            return false;
        }
        raiz = insertar(raiz, dato);
        return true;
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodo, T dato) {
        if (nodo == null) {
            return new NodoBinario<>(dato);
        }
        if (dato.compareTo(nodo.getDato()) < 0) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), dato));
        } else {
            nodo.setDerecho(insertar(nodo.getDerecho(), dato));
        }
        return nodo;
    }

    private boolean existe(T dato) {
        NodoBinario<T> actual = raiz;
        while (actual != null) {
            int comparacion = dato.compareTo(actual.getDato());
            if (comparacion == 0) return true;
            actual = comparacion < 0 ? actual.getIzquierdo() : actual.getDerecho();
        }
        return false;
    }

    public Cola<T> inorden() {
        Cola<T> resultado = new Cola<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(NodoBinario<T> nodo, Cola<T> salida) {
        if (nodo == null) {
            return;
        }
        inorden(nodo.getIzquierdo(), salida);
        salida.encolar(nodo.getDato());
        inorden(nodo.getDerecho(), salida);
    }

    public Cola<T> preorden() {
        Cola<T> resultado = new Cola<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(NodoBinario<T> nodo, Cola<T> salida) {
        if (nodo == null) {
            return;
        }
        salida.encolar(nodo.getDato());
        preorden(nodo.getIzquierdo(), salida);
        preorden(nodo.getDerecho(), salida);
    }

    public Cola<T> postorden() {
        Cola<T> resultado = new Cola<>();
        postorden(raiz, resultado);
        return resultado;
    }

    private void postorden(NodoBinario<T> nodo, Cola<T> salida) {
        if (nodo == null) {
            return;
        }
        postorden(nodo.getIzquierdo(), salida);
        postorden(nodo.getDerecho(), salida);
        salida.encolar(nodo.getDato());
    }

    public Cola<T> amplitud() {
        Cola<T> resultado = new Cola<>();
        if (raiz == null) {
            return resultado;
        }
        Cola<NodoBinario<T>> pendientes = new Cola<>();
        pendientes.encolar(raiz);
        while (!pendientes.estaVacia()) {
            NodoBinario<T> nodo = pendientes.desencolar();
            resultado.encolar(nodo.getDato());
            if (nodo.getIzquierdo() != null) {
                pendientes.encolar(nodo.getIzquierdo());
            }
            if (nodo.getDerecho() != null) {
                pendientes.encolar(nodo.getDerecho());
            }
        }
        return resultado;
    }

    private NodoBinario<T> menor(NodoBinario<T> nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
    }

    /** Elimina usando el menor nodo del subárbol derecho si hay dos hijos. */
    public boolean eliminar(T dato) {
        if (!existe(dato)) {
            return false;
        }
        raiz = eliminar(raiz, dato);
        return true;
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        int comparacion = dato.compareTo(nodo.getDato());
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminar(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminar(nodo.getDerecho(), dato));
        } else {
            // Caso 1 (hoja) y caso 2 (un hijo): el enlace pasa al hijo (o a null).
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            NodoBinario<T> sucesor = menor(nodo.getDerecho());
            nodo.setDato(sucesor.getDato());
            nodo.setDerecho(eliminar(nodo.getDerecho(), sucesor.getDato()));
        }
        return nodo;
    }
}
