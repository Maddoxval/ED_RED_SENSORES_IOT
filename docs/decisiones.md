# Decisiones de diseño — Semana 3
## Búsqueda binaria por timestamp

### Precondición

La búsqueda binaria requiere que las lecturas estén ordenadas
ascendentemente por timestamp.

### Condición actual del proyecto

`GeneradorDatos` produce timestamps en orden cronológico.

### Decisión

Utilizar búsqueda binaria para consultas por timestamp.

### Justificación

La búsqueda binaria reduce el número de comparaciones
de un crecimiento O(n) a un crecimiento O(log n),
siempre que se mantenga la precondición de ordenamiento.

## PM2.5

No se utilizará directamente búsqueda binaria sobre PM2.5
mientras los datos no estén ordenados por ese campo.

El experimento de la Semana 3 demuestra la importancia
de respetar las precondiciones de un algoritmo.

## Pregunta pendiente

¿Conviene ordenar los datos antes de realizar las búsquedas?

Esta pregunta será retomada en la Semana 4.


## 1. Punto de entrada

El proyecto mantiene un único punto de entrada:

```text
IngestaSensores.main()
```

No se crean aplicaciones independientes por semana.

`BancoDePruebas` es una clase auxiliar y no contiene `main`.

## 2. Búsqueda por timestamp

Se utilizan dos estrategias:

- Búsqueda lineal: no requiere ordenamiento.
- Búsqueda binaria: requiere que el arreglo esté ordenado por timestamp.

Los datos sintéticos de `GeneradorDatos` se generan en orden cronológico, por lo que la búsqueda binaria por timestamp cumple su precondición.

## 3. Búsqueda por PM2.5

No se asume que los datos estén ordenados por PM2.5.

Por tanto, la búsqueda binaria por PM2.5 se conserva como experimento para demostrar el efecto de una precondición incumplida.

## 4. Comparación de String

Los identificadores de estación se comparan mediante:

```java
equals()
```

y no mediante:

```java
==
```

porque se necesita comparar contenido.

## 5. Medición

La comparación principal entre algoritmos utiliza el número de comparaciones.

El tiempo en milisegundos se conserva como evidencia experimental, pero no es la única medida utilizada.

## 6. Evolución del proyecto

La Semana 3 agrega una nueva capacidad a la misma plataforma:

```text
Sensores
   ↓
Ingesta
   ↓
Repositorio
   ↓
Búsqueda
   ↓
Medición de eficiencia
```


La Semana 4 podrá extender esta misma arquitectura para estudiar ordenamiento.


## Decisiones semana-4 — Pivote de QuickSort

**Semana:** 4

**Problema:**
QuickSort con el primer elemento como pivote produce particiones
desbalanceadas cuando los datos ya vienen ordenados, que es como llegan
de la red de sensores (orden cronológico). Con 50.000 lecturas ordenadas
el programa terminó en `StackOverflowError` tras 731.635.497 comparaciones.

**Alternativas:**
- Pivote aleatorio.
- Mediana de tres.

**Decisión:**
Se eligió el pivote aleatorio: antes de particionar, se intercambia el
primer elemento del tramo con uno elegido al azar.

**Justificación:**
Con datos ordenados, el pivote aleatorio terminó en 945.672 comparaciones
en vez de fallar. Con datos desordenados pasó de 900.318 a 992.330
comparaciones, un costo pequeño. Es la opción más corta de implementar y
ningún patrón de entrada, como el orden cronológico, la degrada de forma
sistemática.

**Consecuencia:**
Los resultados de QuickSort varían ligeramente entre ejecuciones porque el
pivote es aleatorio. Se pierde reproducibilidad exacta a cambio de evitar el
peor caso con los datos reales de la red.

---

##Ordenamiento y búsqueda

**Problema:**
Ordenar por PM2.5 el mismo arreglo que se consulta por timestamp destruye
el orden cronológico. La lectura sigue existiendo, pero la búsqueda binaria
por timestamp deja de cumplir su precondición (datos ordenados por el
criterio de búsqueda).

**Alternativas:**
- Trabajar sobre una copia.
- Restaurar el orden después del ranking.
- Mantener índices separados por criterio.

**Decisión:**
`ordenarPorPm25` ahora ordena una copia y la devuelve. El arreglo original
no se modifica.

**Justificación:** 
Después del cambio, la consulta binaria por timestamp encontró la lectura en
la posición 73412 con 16 comparaciones, igual que antes del ranking, y el
arreglo siguió ordenado por timestamp. Restaurar el orden costaría un nuevo
ordenamiento cada vez, y los índices separados agregan complejidad que no se
justifica todavía con dos criterios.

**Consecuencia:**
Cada ranking cuesta memoria adicional (una copia del arreglo) y el tiempo de
copiar. A cambio, la búsqueda por timestamp nunca se ve afectada. Si en el
futuro hay muchos criterios o actualizaciones frecuentes, habría que
reconsiderar los índices separados.
