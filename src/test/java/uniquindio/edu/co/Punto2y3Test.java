package uniquindio.edu.co;

import org.junit.jupiter.api.Test;
import uniquindio.edu.co.ejercicios.Punto2Historial;
import uniquindio.edu.co.ejercicios.Punto3Paquetes;
import uniquindio.edu.co.estructuras.Cola;
import uniquindio.edu.co.estructuras.Pila;
import uniquindio.edu.co.model.Paquete;
import uniquindio.edu.co.model.TipoEntrega;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Punto2y3Test {

    // ------------------------------------------------------------------ punto 2

    private Pila<String> historial() {
        Pila<String> pila = new Pila<>();
        for (int i = 1; i <= 6; i++) {
            pila.apilar("A" + i);
        }
        return pila; // A6 → A5 → A4 → A3 → A2 → A1
    }

    @Test
    void segmentoDelEjemplo() {
        Pila<String> original = historial();
        Pila<String> segmento = Punto2Historial.obtenerSegmento(original, 2, 4);
        assertEquals("A5 → A4 → A3", segmento.toString());
        assertEquals("A6 → A5 → A4 → A3 → A2 → A1", original.toString());
    }

    @Test
    void segmentoDeUnSoloElemento() {
        assertEquals("A4", Punto2Historial.obtenerSegmento(historial(), 3, 3).toString());
    }

    @Test
    void segmentoDeTodaLaPila() {
        assertEquals("A6 → A5 → A4 → A3 → A2 → A1", Punto2Historial.obtenerSegmento(historial(), 1, 6).toString());
    }

    @Test
    void limitesInvalidosRetornanPilaVacia() {
        Pila<String> original = historial();
        assertTrue(Punto2Historial.obtenerSegmento(original, 0, 3).estaVacia());
        assertTrue(Punto2Historial.obtenerSegmento(original, 4, 2).estaVacia());
        assertTrue(Punto2Historial.obtenerSegmento(original, 2, 7).estaVacia());
        assertTrue(Punto2Historial.obtenerSegmento(new Pila<String>(), 1, 1).estaVacia());
        assertEquals(6, original.tamanio());
        assertEquals("A6 → A5 → A4 → A3 → A2 → A1", original.toString());
    }

    // ------------------------------------------------------------------ punto 3

    private Cola<Paquete> pendientesDelParcial() {
        Cola<Paquete> cola = new Cola<>();
        cola.encolar(new Paquete("P1", TipoEntrega.NORMAL, 4));
        cola.encolar(new Paquete("P2", TipoEntrega.URGENTE, 7));
        cola.encolar(new Paquete("P3", TipoEntrega.NORMAL, 3));
        cola.encolar(new Paquete("P4", TipoEntrega.URGENTE, 5));
        cola.encolar(new Paquete("P5", TipoEntrega.NORMAL, 6));
        cola.encolar(new Paquete("P6", TipoEntrega.URGENTE, 4));
        cola.encolar(new Paquete("P7", TipoEntrega.NORMAL, 2));
        return cola;
    }

    private String codigos(Cola<Paquete> cola) {
        Cola<Paquete> copia = cola.copia();
        StringBuilder sb = new StringBuilder();
        while (!copia.estaVacia()) {
            sb.append(copia.desencolar().getCodigo());
            if (!copia.estaVacia()) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    @Test
    void seleccionDelParcialCon14Kg() {
        Cola<Paquete> pendientes = pendientesDelParcial();
        Cola<Paquete> seleccionados = Punto3Paquetes.seleccionarPaquetes(pendientes, 14);
        assertEquals("P2,P4,P7", codigos(seleccionados));
        assertEquals("P1,P3,P5,P6", codigos(pendientes));
        assertEquals(0, 14 - Punto3Paquetes.pesoTotal(seleccionados));
    }

    @Test
    void capacidadCeroONegativaNoCambiaNada() {
        Cola<Paquete> pendientes = pendientesDelParcial();
        assertTrue(Punto3Paquetes.seleccionarPaquetes(pendientes, 0).estaVacia());
        assertTrue(Punto3Paquetes.seleccionarPaquetes(pendientes, -5).estaVacia());
        assertEquals("P1,P2,P3,P4,P5,P6,P7", codigos(pendientes));
    }

    @Test
    void capacidadSuficienteParaTodos() {
        Cola<Paquete> pendientes = pendientesDelParcial();
        Cola<Paquete> seleccionados = Punto3Paquetes.seleccionarPaquetes(pendientes, 100);
        // primero urgentes (P2,P4,P6) y luego normales (P1,P3,P5,P7)
        assertEquals("P2,P4,P6,P1,P3,P5,P7", codigos(seleccionados));
        assertTrue(pendientes.estaVacia());
    }

    @Test
    void colaVacia() {
        Cola<Paquete> pendientes = new Cola<>();
        assertTrue(Punto3Paquetes.seleccionarPaquetes(pendientes, 10).estaVacia());
        assertTrue(pendientes.estaVacia());
    }

    @Test
    void siUnUrgenteNoCabeSeContinuaConElSiguiente() {
        Cola<Paquete> pendientes = new Cola<>();
        pendientes.encolar(new Paquete("U1", TipoEntrega.URGENTE, 10));
        pendientes.encolar(new Paquete("U2", TipoEntrega.URGENTE, 3));
        pendientes.encolar(new Paquete("N1", TipoEntrega.NORMAL, 2));
        Cola<Paquete> seleccionados = Punto3Paquetes.seleccionarPaquetes(pendientes, 5);
        assertEquals("U2,N1", codigos(seleccionados));
        assertEquals("U1", codigos(pendientes));
    }
}
