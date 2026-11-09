package uniquindio.edu.co.estructuras;

/**
 * Pila propia (LIFO) implementada con nodos enlazados.
 * No usa ninguna colección de java.util.
 *
 * Operaciones: apilar, desapilar, verTope, estaVacia, tamanio.
 */
public class Pila<T> {

    private Nodo<T> tope;
    private int tamanio;

    /** Agrega un elemento en el tope. */
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamanio++;
    }

    /** Retira y retorna el elemento del tope. */
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        tamanio--;
        return dato;
    }

    /** Retorna el elemento del tope sin retirarlo. */
    public T verTope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return tope.getDato();
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int tamanio() {
        return tamanio;
    }

    /** Retorna una pila nueva con los mismos elementos y el mismo orden. */
    public Pila<T> copia() {
        Pila<T> invertida = new Pila<>();
        Nodo<T> actual = tope;
        while (actual != null) {
            invertida.apilar(actual.getDato());
            actual = actual.getSiguiente();
        }
        Pila<T> copia = new Pila<>();
        while (!invertida.estaVacia()) {
            copia.apilar(invertida.desapilar());
        }
        return copia;
    }

    /** Contenido desde el tope hasta el fondo: "a → b → c". */
    @Override
    public String toString() {
        if (estaVacia()) {
            return "(vacía)";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = tope;
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(" → ");
            }
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }
}
