package uniquindio.edu.co;

import org.junit.jupiter.api.Test;
import uniquindio.edu.co.ejercicios.Punto4ArbolEquipos;
import uniquindio.edu.co.estructuras.ArbolBinarioBusqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Punto4Test {

    @Test
    void recorridosDelAbbDelParcial() {
        ArbolBinarioBusqueda<Integer> arbol = Punto4ArbolEquipos.construirArbol(false);
        assertEquals("6 12 13 16 20 22 41 43 44 47 50 52 60", arbol.inorden().aTexto(" "));
        assertEquals("41 20 16 6 13 12 22 52 47 43 44 50 60", arbol.preorden().aTexto(" "));
        assertEquals("12 13 6 16 22 20 44 43 50 47 60 52 41", arbol.postorden().aTexto(" "));
        assertEquals("41 20 52 16 22 47 60 6 43 50 13 44 12", arbol.amplitud().aTexto(" "));
    }

    @Test
    void contarEquiposEnRango() {
        ArbolBinarioBusqueda<Integer> arbol = Punto4ArbolEquipos.construirArbol(false);
        assertEquals(6, Punto4ArbolEquipos.contarEquiposEnRango(arbol.getRaiz(), 15, 45));
        assertEquals(4, Punto4ArbolEquipos.contarEquiposEnRango(arbol.getRaiz(), 44, 52));
        assertEquals(13, Punto4ArbolEquipos.contarEquiposEnRango(arbol.getRaiz(), 0, 100));
        assertEquals(0, Punto4ArbolEquipos.contarEquiposEnRango(arbol.getRaiz(), 50, 10));
        assertEquals(0, Punto4ArbolEquipos.contarEquiposEnRango(null, 0, 100));
    }

    @Test
    void eliminarElNodo41UsaElSucesor() {
        ArbolBinarioBusqueda<Integer> arbol = Punto4ArbolEquipos.construirArbol(false);
        assertTrue(arbol.eliminar(41));
        assertEquals("43 20 16 6 13 12 22 52 47 44 50 60", arbol.preorden().aTexto(" "));
        assertEquals("6 12 13 16 20 22 43 44 47 50 52 60", arbol.inorden().aTexto(" "));
        assertFalse(arbol.eliminar(999));
    }
}
