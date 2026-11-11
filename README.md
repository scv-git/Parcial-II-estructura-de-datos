# Parcial II · Estructuras de Datos

Universidad del Quindío · Programa de Ingeniería de Sistemas y Computación
Profesor: Jhan Carlos Martínez Ceballos
Integrantes: Miguel Ángel Maya Colorado, Santiago Cifuentes Vega

Proyecto Java con las soluciones de los ejercicios del Parcial II. Todos los ejercicios de código usan **estructuras propias** (pila, cola y árbol binario de búsqueda), sin colecciones de `java.util`.

## Requisitos

- JDK 17 o superior.
- Maven 3.8 o superior (opcional, también se puede ejecutar desde un IDE).

## Estructura del proyecto

```
src/main/java/uniquindio/edu/co/
├── Main.java                      Punto de entrada
├── estructuras/                   Estructuras de datos propias
│   ├── Nodo.java                  Nodo simple (pila y cola)
│   ├── Pila.java                  Pila (LIFO)
│   ├── Cola.java                  Cola (FIFO)
│   ├── NodoBinario.java           Nodo de árbol binario
│   └── ArbolBinarioBusqueda.java  ABB: insertar, eliminar y recorridos
├── model/                         Clases de datos
│   ├── Caja.java
│   ├── Paquete.java
│   └── TipoEntrega.java           URGENTE / NORMAL
├── util/
│   └── EvaluadorExpresiones.java  Infija a postfija y evaluación con pila
└── ejercicios/
    ├── Punto1Cajas.java
    ├── Punto2Historial.java
    ├── Punto3Paquetes.java
    ├── Punto4ArbolEquipos.java
    └── Punto5Arboles.java

src/test/java/uniquindio/edu/co/   Pruebas JUnit 5 (Punto1Test, Punto2y3Test, Punto4Test)
```

## Estructuras propias

- **Pila<T>**: apilar, desapilar, ver tope, está vacía, tamaño.
- **Cola<T>**: encolar, desencolar, ver frente, está vacía, tamaño.
- **ArbolBinarioBusqueda<T>**: insertar (rechaza duplicados), eliminar, y recorridos inorden, preorden, postorden y amplitud.

## Ejercicios

### Punto 1 · Retiro de cajas y evaluación de expresiones

`retirarCaja(pila, codigo)` retira la caja con el código dado y reorganiza las restantes según la paridad de su valor. Las posiciones se cuentan desde la cima, empezando en 1, después de retirar la caja y antes de reorganizar.

- Si el valor retirado es **par**, las cajas en posiciones impares quedan arriba, seguidas de las pares.
- Si el valor retirado es **impar**, las cajas en posiciones pares quedan arriba, seguidas de las impares.
- En cada grupo se conserva el orden relativo.
- Retorna `true` si retiró la caja. Si el código no existe o la pila está vacía, retorna `false` y la pila queda intacta.

**Cómo funciona:** pasa todo a una pila auxiliar, vuelve a apilar omitiendo la caja buscada, separa las cajas por posición impar y par en dos pilas auxiliares y las apila de nuevo en el orden que corresponde.

**Prueba (retirar C3, C4 y C7):**

| Paso | Resultado | Pila (tope → fondo) |
|---|---|---|
| Inicial | | C7(5), C6(9), C5(3), C4(8), C3(6), C2(4), C1(2) |
| Retirar C3 | `true` | C7(5), C5(3), C2(4), C6(9), C4(8), C1(2) |
| Retirar C4 | `true` | C7(5), C2(4), C1(2), C5(3), C6(9) |
| Retirar C7 | `true` | C1(2), C6(9), C2(4), C5(3) |

**Evaluación de la expresión** `(A + B) × C − (A − B) × D` con las cuatro cajas finales:

- A = 2, B = 9 / 2 = 4.5, C = 4 + 2 = 6, D = (3 + 6) × 2 = 18.
- Postfija: `A B + C * A B - D * -`
- Con valores: `2 4.5 + 6 * 2 4.5 - 18 * -`
- Se evalúa con una pila, mostrando en cada paso el elemento procesado, la operación y el contenido de la pila.
- Resultado final: **84**.

### Punto 2 · Segmento del historial

`obtenerSegmento(pila, inicio, fin)` retorna una pila nueva con las acciones entre las posiciones `inicio` y `fin`, ambas incluidas, contadas desde la cima desde 1. La acción en `inicio` queda en la cima del resultado.

- La pila original queda con los mismos elementos en el mismo orden.
- Si `inicio < 1`, `inicio > fin` o `fin` supera el tamaño, retorna una pila vacía.

**Ejemplo:** pila `A6 → A5 → A4 → A3 → A2 → A1`, con `inicio = 2` y `fin = 4`, da `A5 → A4 → A3`.

### Punto 3 · Selección de paquetes para una jornada

`seleccionarPaquetes(cola, capacidad)` retorna una cola con los paquetes seleccionados y los retira de la cola de pendientes.

- Primero se consideran los paquetes urgentes y después los normales, cada grupo en su orden de registro.
- Un paquete se selecciona si su peso es menor o igual a la capacidad restante. Si no cabe, sigue pendiente y se evalúa el siguiente.
- Los pendientes conservan su orden original.
- Si la capacidad es menor o igual a cero, retorna una cola vacía y no cambia los pendientes.

**Implementación recursiva:** no usa `for`, `while` ni `do-while`. Una función recursiva recorre la cola tantas veces como elementos tenga, desencolando cada paquete y volviéndolo a encolar si no se selecciona.

**Prueba (capacidad 14 kg):**

- Seleccionados: P2 (7 kg), P4 (5 kg), P7 (2 kg).
- Pendientes: P1, P3, P5, P6.
- Capacidad restante: 0 kg.

### Punto 4 · Árbol binario de búsqueda

**a) `contarEquiposEnRango(raiz, inferior, superior)`:** función recursiva que cuenta los códigos dentro del rango, incluidos los extremos. Aprovecha el orden del árbol:

- Si el código del nodo es menor que el límite inferior, solo busca en el subárbol derecho.
- Si es mayor que el límite superior, solo busca en el izquierdo.
- Si está dentro del rango, cuenta 1 y revisa ambos lados.
- Si el árbol está vacío o el límite inferior supera al superior, retorna 0.

**b) Eliminación:** se insertan 41, 20, 52, 16, 6, 13, 12, 60, 22, 47, 50, 60, 43, 44. El segundo 60 se ignora porque los códigos son únicos.

| Recorrido | Árbol original | Después de eliminar el 41 |
|---|---|---|
| Inorden | 6 12 13 16 20 22 41 43 44 47 50 52 60 | 6 12 13 16 20 22 43 44 47 50 52 60 |
| Preorden | 41 20 16 6 13 12 22 52 47 43 44 50 60 | 43 20 16 6 13 12 22 52 47 44 50 60 |
| Postorden | 12 13 6 16 22 20 44 43 50 47 60 52 41 | 12 13 6 16 22 20 44 50 47 60 52 43 |
| Amplitud | 41 20 52 16 22 47 60 6 43 50 13 44 12 | 43 20 52 16 22 47 60 6 44 50 13 12 |

**Opción elegida al eliminar el 41:** como tiene dos hijos, se reemplaza por el menor valor de su subárbol derecho (sucesor inorden), que es el 43. El 44, hijo derecho del 43, sube a ocupar su lugar. Así el árbol conserva la propiedad del ABB.

### Punto 5 · AVL y Rojo-Negro

Este punto se resolvió a mano, como pide el enunciado, y está en el documento de respuestas con cada paso, rotación y factor de equilibrio (FE = altura del subárbol derecho − altura del izquierdo).

- **a)** AVL con las palabras: alma, beso, azul, cine, eco, dedo, bota, hilo, lago, isla, nube, pato, oro, mapa, gato.
- **b)** Eliminar «cine»: se reemplaza por el mayor valor de su subárbol izquierdo (bota), y se corrige con una rotación simple a la izquierda.
- **c)** Árbol Rojo-Negro construido con el recorrido postorden del AVL resultante: alma, beso, azul, dedo, gato, hilo, eco, bota, mapa, lago, oro, pato, nube, isla.

`Punto5Arboles` solo imprime la lista de palabras y el postorden, como guía.
