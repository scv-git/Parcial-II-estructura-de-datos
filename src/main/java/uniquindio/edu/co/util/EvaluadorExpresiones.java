package uniquindio.edu.co.util;

import uniquindio.edu.co.estructuras.Cola;
import uniquindio.edu.co.estructuras.Pila;

/** Convierte y evalúa expresiones usando las pilas y colas propias. */
public final class EvaluadorExpresiones {

    private EvaluadorExpresiones() {
    }

    public static Cola<String> infijaAPostfija(String expresion) {
        return infijaAPostfija(expresion, false);
    }

    public static Cola<String> infijaAPostfija(String expresion, boolean mostrarTabla) {
        Cola<String> salida = new Cola<>();
        Pila<String> operadores = new Pila<>();
        StringBuilder operando = new StringBuilder();
        int paso = 0;

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);
            if (Character.isLetterOrDigit(caracter) || caracter == '.') {
                operando.append(caracter);
                continue;
            }
            if (operando.length() > 0) {
                salida.encolar(operando.toString());
                operando.setLength(0);
            }
            if (Character.isWhitespace(caracter)) {
                continue;
            }

            String token = String.valueOf(caracter);
            if (token.equals("(")) {
                operadores.apilar(token);
            } else if (token.equals(")")) {
                while (!operadores.verTope().equals("(")) {
                    salida.encolar(operadores.desapilar());
                }
                operadores.desapilar();
            } else if (esOperador(token)) {
                while (!operadores.estaVacia()
                        && !operadores.verTope().equals("(")
                        && precedencia(operadores.verTope()) >= precedencia(token)) {
                    salida.encolar(operadores.desapilar());
                }
                operadores.apilar(token);
            }
            paso++;
            if (mostrarTabla) {
                System.out.println("Paso " + paso + ": " + token + " | postfija: " + salida.aTexto(" "));
            }
        }
        if (operando.length() > 0) {
            salida.encolar(operando.toString());
        }
        while (!operadores.estaVacia()) {
            salida.encolar(operadores.desapilar());
        }
        return salida;
    }

    public static Cola<String> reemplazarVariables(Cola<String> postfija, String[] nombres, double[] valores) {
        Cola<String> entrada = postfija.copia();
        Cola<String> resultado = new Cola<>();
        while (!entrada.estaVacia()) {
            String token = entrada.desencolar();
            for (int i = 0; i < nombres.length; i++) {
                if (nombres[i].equals(token)) {
                    token = formato(valores[i]);
                    break;
                }
            }
            resultado.encolar(token);
        }
        return resultado;
    }

    public static double evaluarPostfija(Cola<String> postfija, boolean mostrarTabla) {
        Cola<String> entrada = postfija.copia();
        Pila<Double> pila = new Pila<>();
        int paso = 0;
        while (!entrada.estaVacia()) {
            String token = entrada.desencolar();
            String operacion;
            if (esOperador(token)) {
                double segundo = pila.desapilar();
                double primero = pila.desapilar();
                double resultado = operar(token, primero, segundo);
                pila.apilar(resultado);
                operacion = primero + " " + token + " " + segundo + " = " + resultado;
            } else {
                pila.apilar(Double.parseDouble(token));
                operacion = "se apila " + token;
            }
            paso++;
            if (mostrarTabla) {
                System.out.println("Paso " + paso + " | " + token + " | " + operacion
                        + " | pila: " + pila);
            }
        }
        return pila.desapilar();
    }

    private static boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static int precedencia(String operador) {
        return operador.equals("*") || operador.equals("/") ? 2 : 1;
    }

    private static double operar(String operador, double primero, double segundo) {
        switch (operador) {
            case "+": return primero + segundo;
            case "-": return primero - segundo;
            case "*": return primero * segundo;
            case "/": return primero / segundo;
            default: throw new IllegalArgumentException("Operador no soportado: " + operador);
        }
    }

    public static String formato(double valor) {
        return valor == Math.rint(valor) ? String.valueOf((long) valor) : String.valueOf(valor);
    }
}
