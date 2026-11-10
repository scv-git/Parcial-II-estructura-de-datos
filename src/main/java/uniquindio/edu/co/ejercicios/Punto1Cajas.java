package uniquindio.edu.co.ejercicios;

import uniquindio.edu.co.estructuras.Cola;
import uniquindio.edu.co.estructuras.Pila;
import uniquindio.edu.co.model.Caja;
import uniquindio.edu.co.util.EvaluadorExpresiones;

/**
 * Punto 1 - Retiro de cajas y evaluación de expresiones.
 */
public final class Punto1Cajas {

    /**
     * Expresión del parcial: (A + B) × C - (A - B) × D.
     */
    public static final String EXPRESION = "(A + B) * C - (A - B) * D";

    private Punto1Cajas() {
    }

    // ------------------------------------------------------------------ retirarCaja

    /**
     * Retira la caja con el código dado y reorganiza las restantes según la paridad de su valor.
     * <ul>
     *   <li>Valor retirado PAR: posiciones impares arriba, luego las pares.</li>
     *   <li>Valor retirado IMPAR: posiciones pares arriba, luego las impares.</li>
     * </ul>
     * Las posiciones se cuentan desde la cima (empezando en 1) DESPUÉS de retirar la caja y
     * ANTES de reorganizar. En cada grupo se conserva el orden relativo.
     *
     * @return true si encontró y retiró la caja; false si el código no existe o la pila está vacía
     * (en ese caso la pila queda intacta).
     */
    public static boolean retirarCaja(Pila<Caja> pila, String codigo) {
        if (pila.estaVacia()) {
            return false;
        }

        // 1) Se pasa todo a una pila auxiliar (queda invertida: el fondo original queda arriba).
        Pila<Caja> auxiliar = new Pila<>();
        while (!pila.estaVacia()) {
            auxiliar.apilar(pila.desapilar());
        }

        // 2) Se devuelve a la pila original omitiendo la caja buscada (el orden original se restablece).
        Caja retirada = null;
        while (!auxiliar.estaVacia()) {
            Caja caja = auxiliar.desapilar();
            if (retirada == null && caja.getCodigo().equals(codigo)) {
                retirada = caja;
            } else {
                pila.apilar(caja);
            }
        }
        if (retirada == null) {
            return false; // no existía: la pila quedó exactamente igual
        }

        // 3) Se reorganizan las cajas restantes según la paridad del valor retirado.
        reorganizar(pila, retirada.getValor() % 2 == 0);
        return true;
    }

    /**
     * Separa las cajas por posición (impar/par, contando desde la cima desde 1) y las vuelve a apilar
     * dejando arriba el grupo que corresponda.
     */
    private static void reorganizar(Pila<Caja> pila, boolean valorRetiradoPar) {
        Pila<Caja> posicionesImpares = new Pila<>();
        Pila<Caja> posicionesPares = new Pila<>();

        int posicion = 1;
        while (!pila.estaVacia()) {
            Caja caja = pila.desapilar();
            if (posicion % 2 != 0) {
                posicionesImpares.apilar(caja);
            } else {
                posicionesPares.apilar(caja);
            }
            posicion++;
        }
        // Cada pila auxiliar tiene arriba a la caja que estaba MÁS ABAJO en su grupo; al vaciarla
        // sobre la pila original, ese orden se invierte otra vez y se conserva el orden relativo.
        Pila<Caja> parteInferior = valorRetiradoPar ? posicionesPares : posicionesImpares;
        Pila<Caja> parteSuperior = valorRetiradoPar ? posicionesImpares : posicionesPares;
        vaciarSobre(parteInferior, pila);
        vaciarSobre(parteSuperior, pila);
    }
    private static void vaciarSobre(Pila<Caja> origen , Pila<Caja> destino) {
        while (!origen.estaVacia()) {
            destino.apilar(origen.desapilar());
        }
    }
    public static void ejecutar(){
        System.out.println("Ejecutando Punto1Cajas");
        System.out.println("======================");
        Pila<Caja> pila = new Pila<>();
        pila.apilar(new Caja("C1",2));
        pila.apilar(new Caja("C2",4));
        pila.apilar(new Caja("C3",6));
        pila.apilar(new Caja("C4",8));
        pila.apilar(new Caja("C5",3));
        pila.apilar(new Caja("C6",9));
        pila.apilar(new Caja("C7",5));
        System.out.println("Pila inicial (tope -> fondo"+pila);
        System.out.println();

        String[] retiros = {"C3", "C4", "C7"};
        for (int i = 0; i < retiros.length; i++) {
            boolean resultado = retirarCaja(pila, retiros[i]);
            System.out.println("Llamada " + (i + 1) + ": retirarCaja(pila, \"" + retiros[i] + "\") → " + resultado);
            System.out.println("   Pila (tope → fondo): " + pila);
        }
        System.out.println();
        System.out.println("Pila final (cima → fondo): " + pila);
        System.out.println();

        evaluarExpresion(pila);

    }
    private static void evaluarExpresion(Pila<Caja> pilaFinal) {
        System.out.println("Evaluación de la expresión: (A + B) × C − (A − B) × D");
        System.out.println("-----------------------------------------------------");

        // Se leen las 4 cajas de la cima al fondo y luego se restablece la pila.
        Caja primera = pilaFinal.desapilar();
        Caja segunda = pilaFinal.desapilar();
        Caja tercera = pilaFinal.desapilar();
        Caja cuarta = pilaFinal.desapilar();
        pilaFinal.apilar(cuarta);
        pilaFinal.apilar(tercera);
        pilaFinal.apilar(segunda);
        pilaFinal.apilar(primera);

        double a = primera.getValor();
        double b = segunda.getValor() / a;
        double c = tercera.getValor() + a;
        double d = (cuarta.getValor() + c) * a;

        System.out.println("Paso 1. Valores asignados (primera caja = la de la cima):");
        System.out.println("   A = valor de la primera caja " + primera + " = " + EvaluadorExpresiones.formato(a));
        System.out.println("   B = valor de la segunda caja " + segunda + " / A = " + segunda.getValor() + " / "
                + EvaluadorExpresiones.formato(a) + " = " + EvaluadorExpresiones.formato(b));
        System.out.println("   C = valor de la tercera caja " + tercera + " + A = " + tercera.getValor() + " + "
                + EvaluadorExpresiones.formato(a) + " = " + EvaluadorExpresiones.formato(c));
        System.out.println("   D = (valor de la cuarta caja " + cuarta + " + C) × A = (" + cuarta.getValor() + " + "
                + EvaluadorExpresiones.formato(c) + ") × " + EvaluadorExpresiones.formato(a) + " = "
                + EvaluadorExpresiones.formato(d));
        System.out.println();

        System.out.println("Paso 2. Conversión de infija a postfija (pila de operadores):");
        System.out.println();
        Cola<String> postfija = EvaluadorExpresiones.infijaAPostfija(EXPRESION, true);
        System.out.println();
        System.out.println("   Expresión postfija: " + postfija.aTexto(" "));
        System.out.println();

        String[] nombres = {"A", "B", "C", "D"};
        double[] valores = {a, b, c, d};
        Cola<String> conValores = EvaluadorExpresiones.reemplazarVariables(postfija, nombres, valores);
        System.out.println("Paso 3. Reemplazo de variables por sus valores:");
        System.out.println("   " + conValores.aTexto(" "));
        System.out.println();

        System.out.println("Paso 4. Evaluación con una pila:");
        System.out.println();
        double resultado = EvaluadorExpresiones.evaluarPostfija(conValores, true);
        System.out.println();

        System.out.println("Paso 5. Resultado final de la expresión = " + EvaluadorExpresiones.formato(resultado));
        double verificacion = (a + b) * c - (a - b) * d;
        System.out.println("   (Verificación directa en Java: " + EvaluadorExpresiones.formato(verificacion) + ")");
        System.out.println();

    }
}