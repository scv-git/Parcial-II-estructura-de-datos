package uniquindio.edu.co.estructuras;

/**
 * Cola propia (FIFO) implementada con nodos enlazados.
 * No usa ninguna colección de java.util.
 *
 * Operaciones: encolar, desencolar, verFrente, estaVacia, tamanio.
 */
public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    /** Agrega un elemento al final de la cola. */
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        tamanio++;
    }

    /** Retira y retorna el elemento del frente. */
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return dato;
    }

    /** Retorna el elemento del frente sin retirarlo. */
    public T verFrente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return frente.getDato();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamanio() {
        return tamanio;
    }

    /** Retorna una cola nueva con los mismos elementos y el mismo orden. */
    public Cola<T> copia() {
        Cola<T> copia = new Cola<>();
        Nodo<T> actual = frente;
        while (actual != null) {
            copia.encolar(actual.getDato());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    /** Contenido desde el frente hasta el final, separado por el texto dado. */
    public String aTexto(String separador) {
        if (estaVacia()) {
            return "(vacía)";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = frente;
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(separador);
            }
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    /** Contenido desde el frente hasta el final: "a → b → c". */
    @Override
    public String toString() {
        return aTexto(" → ");
    }
}
