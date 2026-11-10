package uniquindio.edu.co;

import org.junit.jupiter.api.Test;
import uniquindio.edu.co.ejercicios.Punto1Cajas;
import uniquindio.edu.co.estructuras.Cola;
import uniquindio.edu.co.estructuras.Pila;
import uniquindio.edu.co.model.Caja;
import uniquindio.edu.co.util.EvaluadorExpresiones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Punto1Test {

    private Pila<Caja> pilaDelParcial() {
        Pila<Caja> pila = new Pila<>();
        pila.apilar(new Caja("C1", 2));
        pila.apilar(new Caja("C2", 4));
        pila.apilar(new Caja("C3", 6));
        pila.apilar(new Caja("C4", 8));
        pila.apilar(new Caja("C5", 3));
        pila.apilar(new Caja("C6", 9));
        pila.apilar(new Caja("C7", 5));
        return pila;
    }

    @Test
    void secuenciaDelParcial() {
        Pila<Caja> pila = pilaDelParcial();
        assertEquals("C7(5) → C6(9) → C5(3) → C4(8) → C3(6) → C2(4) → C1(2)", pila.toString());

        assertTrue(Punto1Cajas.retirarCaja(pila, "C3"));
        assertEquals("C7(5) → C5(3) → C2(4) → C6(9) → C4(8) → C1(2)", pila.toString());

        assertTrue(Punto1Cajas.retirarCaja(pila, "C4"));
        assertEquals("C7(5) → C2(4) → C1(2) → C5(3) → C6(9)", pila.toString());

        assertTrue(Punto1Cajas.retirarCaja(pila, "C7"));
        assertEquals("C1(2) → C6(9) → C2(4) → C5(3)", pila.toString());
    }

    @Test
    void codigoInexistenteDejaLaPilaIntacta() {
        Pila<Caja> pila = pilaDelParcial();
        String antes = pila.toString();
        assertFalse(Punto1Cajas.retirarCaja(pila, "C99"));
        assertEquals(antes, pila.toString());
        assertEquals(7, pila.tamanio());
    }

    @Test
    void pilaVaciaRetornaFalse() {
        assertFalse(Punto1Cajas.retirarCaja(new Pila<>(), "C1"));
    }

    @Test
    void retirarLaUnicaCajaDejaPilaVacia() {
        Pila<Caja> pila = new Pila<>();
        pila.apilar(new Caja("C1", 7));
        assertTrue(Punto1Cajas.retirarCaja(pila, "C1"));
        assertTrue(pila.estaVacia());
    }

    @Test
    void retirarCajaDelTope() {
        // Se retira el tope (C3=6, par). Quedan C2, C1 → posiciones 1 y 2 → impares arriba: C2, C1 (igual orden).
        Pila<Caja> pila = new Pila<>();
        pila.apilar(new Caja("C1", 2));
        pila.apilar(new Caja("C2", 4));
        pila.apilar(new Caja("C3", 6));
        assertTrue(Punto1Cajas.retirarCaja(pila, "C3"));
        assertEquals("C2(4) → C1(2)", pila.toString());
    }

    @Test
    void valorImparDejaPosicionesParesArriba() {
        // Pila (tope→fondo): X1(1) A(10) B(20) C(30) D(40). Se retira X1 (valor impar).
        // Posiciones tras retirar: A=1, B=2, C=3, D=4 → pares arriba: B, D; luego impares: A, C.
        Pila<Caja> pila = new Pila<>();
        pila.apilar(new Caja("D", 40));
        pila.apilar(new Caja("C", 30));
        pila.apilar(new Caja("B", 20));
        pila.apilar(new Caja("A", 10));
        pila.apilar(new Caja("X1", 1));
        assertTrue(Punto1Cajas.retirarCaja(pila, "X1"));
        assertEquals("B(20) → D(40) → A(10) → C(30)", pila.toString());
    }

    @Test
    void evaluacionDeLaExpresion() {
        Cola<String> postfija = EvaluadorExpresiones.infijaAPostfija(Punto1Cajas.EXPRESION, false);
        assertEquals("A B + C * A B - D * -", postfija.aTexto(" "));

        Cola<String> conValores = EvaluadorExpresiones.reemplazarVariables(postfija,
                new String[]{"A", "B", "C", "D"}, new double[]{2, 4.5, 6, 18});
        assertEquals("2 4.5 + 6 * 2 4.5 - 18 * -", conValores.aTexto(" "));
        assertEquals(84.0, EvaluadorExpresiones.evaluarPostfija(conValores, false), 1e-9);
    }
}
