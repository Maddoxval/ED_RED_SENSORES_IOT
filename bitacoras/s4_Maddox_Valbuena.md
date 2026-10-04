# Bitacora individual - Semana 4

## 1. Datos de la actividad

- **Estudiante:** Maddox Santiago Valbuena Ossa
- **Semana:** 4
- **Fecha del laboratorio:** [2026-10-02]
- **Fecha del taller:** [2026-10-02]
- **Tema principal:** Ordenamientos simples y avanzados, y comparación de eficiencia
- **Pregunta de la semana:** Si ordenar es necesario para buscar rápidamente, cuanto cuesta ordenar y que consecuencias tiene hacerlo?

## 2. Predicción antes de ejecutar

1. **¿Qué creo que va a ocurrir?**
    - Exp. 5 (escrita antes de ejecutar): Creí que la búsqueda binaria
      por timestamp seguiría encontrando la lectura después de ordenar por PM2.5.
    - Exp. 1 a 4 (escrito después de ejecutar, no es predicción): antes de
      medir no tenía una idea clara de cuál algoritmo sería mejor, porque aún no
      entendía bien como funcionaban burbuja, selección e inserción. Tampoco
      esperaba que QuickSort pudiera fallar con datos ordenados: lo daba por
      "rápido" sin más.

2. **¿Qué parte del programa o del algoritmo puede fallar?**
   El supuesto de que la búsqueda binaria por timestamp sigue funcionando
   después de reordenar el arreglo por PM2.5 (Paso 3 del Exp. 5).

3. **¿Cómo comprobaré mi predicción?**
   Ejecutando el Experimento 5 y comparando la posición que devuelve la
   búsqueda binaria en el Paso 1 contra la del Paso 3, y contra la búsqueda
   lineal de verificación.

## 3. Evidencia del laboratorio

### Resultado observado

Mediciones propias, 10.000 lecturas desordenadas (Exp. 1):

| Algoritmo | Comparaciones | Intercambios |
|---|---:|---:|
| Burbuja | 49.995.000 | 24.928.244 |
| Seleccion | 49.995.000 | 9.994 |
| Insercion | 24.938.233 | 24.928.244 |

10.000 lecturas ya ordenadas (Exp. 2), burbuja antes y después de la bandera:

| Algoritmo | Comparaciones |
|---|---:|
| Burbuja sin bandera | 49.995.000 |
| Burbuja con bandera | 9.999 |
| Seleccion | 49.995.000 |
| Insercion | 9.999 |

Simples contra avanzados (Exp. 3), lecturas desordenadas:

| n | Algoritmo | Comparaciones | Intercambios | Tiempo |
|---:|---|---:|---:|---:|
| 1.000 | Insercion | 242.787 | 241.797 | 3 ms |
| 1.000 | MergeSort | 8.684 | 9.976 | 1 ms |
| 1.000 | HeapSort | 16.786 | 9.065 | 1 ms |
| 10.000 | Insercion | 24.938.233 | 24.928.244 | 165 ms |
| 10.000 | MergeSort | 120.396 | 133.616 | 3 ms |
| 10.000 | HeapSort | 235.434 | 124.208 | 5 ms |
| 100.000 | Insercion | 2.497.222.762 | 2.497.122.770 | 34.243 ms |
| 100.000 | MergeSort | 1.536.325 | 1.668.928 | 37 ms |
| 100.000 | HeapSort | 3.019.556 | 1.574.970 | 70 ms |

Razones de crecimiento en comparaciones:

| Algoritmo | 1.000 a 10.000 | 10.000 a 100.000 |
|---|---:|---:|
| Insercion | ~102,7 | ~100,1 |
| MergeSort | ~13,9 | ~12,8 |
| HeapSort | ~14,0 | ~12,8 |

QuickSort con 50.000 lecturas (Exp. 4):

| Caso | Pivote = primer elemento | Pivote aleatorio |
|---|---|---:|
| Desordenadas | 900.318 comparaciones | 992.330 comparaciones |
| Ordenadas | `StackOverflowError` tras 731.635.497 comparaciones | 945.672 comparaciones |

Exp. 5 (100.000 lecturas, ranking sobre copia): Paso 1 posición 73412 con 16
comparaciones; Paso 2 ranking de 5.0 a 60.0; Paso 3 sigue ordenado por
timestamp (`true`), posición 73412 con 16 comparaciones, igual a la busqueda
lineal.

### Diferencia entre la prediccion y el resultado

Exp. 5: predije A (la encuentra normal). Con la solución de la copia, la
búsqueda si la encuentra, pero eso no prueba mi predicción, porque no ejecute la
version original que ordenaba el mismo arreglo.
[PENDIENTE: ejecutar esa version (cambiar la línea a
`datos = Ordenador.ordenarPorPm25(datos);`) y escribir aquí que paso en el
Paso 3. Si no se hace, dejar escrito que no se hizo.]

### Error o comportamiento inesperado

- **¿Qué ocurrió?** QuickSort con pivote en el primer elemento termino en
  `StackOverflowError` con 50.000 lecturas en orden cronológico.
- **¿Por qué ocurrió?** Con datos ya ordenados el pivote es siempre el menor, el
  grupo de menores queda vacío y cada llamada solo quita un elemento. Eso
  genera unas 50.000 llamadas anidadas y la pila de Java no las soporta.
- **¿Cómo lo corregimos?** Pivote aleatorio en `particionar`. El Caso B paso de
  fallar a 945.672 comparaciones.

## 4. Explicación en lenguaje llano

- Ordenar es poner cosas en fila según un criterio, como las lecturas por hora.
Unos métodos revisan todo una y otra vez, y otros van armando la fila poco a
poco o dividen el trabajo en grupos chicos. Cuando los datos ya vienen en
orden, algunos métodos lo notan y casi no trabajan, y otros hacen todo el esfuerzo igual. 
Además, ordenar por un criterio nuevo deshace el orden 
anterior, y eso puede dañar otras partes del sistema.

### Ejemplo o analogía

Ordenar cartas en la mano es inserción: tomo una carta nueva y la meto en su
lugar entre las que ya tengo ordenadas. Cada carta es una lectura y mi mano
ordenada es la parte del arreglo que ya está lista. Si las cartas ya vienen
ordenadas, cada una va al final con una sola mirada, y por eso inserción hizo
solo 9.999 comparaciones con 10.000 datos ordenados. La analogía deja de ser
exacta porque con una mano real "veo" donde va la carta de golpe, mientras que
el algoritmo debe comparar una por una.

## 5. El vacío que encontré

- **Mi duda concreta es:** cómo funcionan burbuja, selección e inserción, y por
  qué inserción hace solo 9.999 comparaciones con datos ordenados mientras que
  selección sigue en casi 50 millones.
- **Lo que ya puedo explicar es:** por qué QuickSort con pivote en el primer
  elemento falla con datos ordenados (cada paso quita un solo elemento), y por
  qué el ranking por PM2.5 sobre el mismo arreglo dana la búsqueda binaria.
- **Para resolver la duda, consulte:** un ejemplo paso a paso con `[5, 2, 4, 1]`
  explicado con el asistente de IA (Claude), y los resultados de los
  Experimentos 1 y 2.
- **Ahora lo entiendo así:** burbuja compara vecinos y empuja el mayor al final;
  selección busca el menor de lo que queda y lo coloca en su posición, por eso
  siempre revisa todo aunque esté ordenado; inserción mete cada elemento en su
  lugar dentro de la parte ya ordenada, asi que con datos ordenados cada
  elemento queda al final con una sola comparación.

## 6. Trazado de la solución

QuickSort con pivote = primer elemento sobre `[1, 2, 3, 4, 5]` (ya ordenado):

| Paso | Estado de los datos | Decision o resultado |
|---|---|---|
| 1 | `[1, 2, 3, 4, 5]`, pivote 1 | Menores: `[]`. Mayores: `[2, 3, 4, 5]`. 4 comparaciones |
| 2 | `[2, 3, 4, 5]`, pivote 2 | Menores: `[]`. Mayores: `[3, 4, 5]`. 3 comparaciones |
| 3 | `[3, 4, 5]`, pivote 3 | Menores: `[]`. Mayores: `[4, 5]`. 2 comparaciones |
| 4 | `[4, 5]`, pivote 4 | Menores: `[]`. Mayores: `[5]`. 1 comparacion |

Cada paso solo descarta un elemento, en vez de partir el problema a la mitad.
Con n elementos son n(n-1)/2 comparaciones y n llamadas anidadas; con
n = 50.000 eso agota la pila.

## 7. Decisión de diseño

- **Problema que debíamos resolver:** Ordenar por PM2.5 para el ranking dejaba
  el arreglo sin orden por timestamp, y la búsqueda binaria por timestamp
  exige ese orden.
- **Estructura, algoritmo o estrategia elegida:** `ordenarPorPm25` ordena una
  copia y la devuelve; el original no se toca (DEC-05).
- **Alternativa descartada:** Restaurar el orden después del ranking
  (reordenar por timestamp cada vez), o mantener índices separados.
- **Por qué elegimos la primera:** Es la más simple y no afecta las consultas.
  Costo: memoria extra y el tiempo de copiar.
- **Qué evidencia respalda la decision:** Exp. 5, Paso 3: ordenado por
  timestamp `true`, posición 73412 en 16 comparaciones, igual que en el Paso 1.

Segunda decisión: pivote aleatorio en QuickSort, respaldado por el
Exp. 4 (de `StackOverflowError` a 945.672 comparaciones).

## 8. Aporte al proyecto

- **Archivo(s) o módulo(s) trabajado(s):** `src/Ordenador.java`,
  `src/BancoDeOrdenamiento.java`, `src/IngestaSensores.java`,
  `docs/decisiones.md`.
- **Cambio realizado:** bandera de corte temprano en burbuja, pivote aleatorio
  en QuickSort, ranking por PM2.5 sobre una copia, y los cinco experimentos
  invocados desde `IngestaSensores`.
- **Como se conecta con la capa anterior:** los experimentos usan
  `BuscadorLecturas` y `LecturaSensor` de la Semana 3, y se llaman desde el
  unico `main`.
- **Que queda pendiente para la siguiente semana:** construir el monticulo que
  HeapSort usa como caja negra (semana 6); ejecutar la version original del
  ranking para observar el fallo; merge a `main` y tag `H1`.

## 9. Commits realizados

Para ver tus hashes: `git log --oneline`.

| Commit | Mensaje | Que demuestra |
|---|---|---|
| `[hash]` | `[mensaje]` | [TUYO] |
| `[hash]` | `[mensaje]` | [TUYO] |

## 10. Reexplicación final

> Ordenar cuesta mucho y el costo crece distinto según el algoritmo: cuando n
> se multiplica por 10, inserción hace unas 100 veces más comparaciones y
> MergeSort unas 13. El costo también depende de como llegan los datos:
> QuickSort con pivote fijo fallo con datos ya ordenados. Y ordenar por otro
> criterio destruye el orden que necesita la búsqueda binaria. Por eso elegí
> pivote aleatorio y ordenar una copia: cuestan poco y evitan el peor caso y el
> efecto colateral.

## 11. Reflexión individual

1. **Lo que ahora puedo hacer y antes no podía:**
   Comparar algoritmos con comparaciones e intercambios en vez de solo el
   reloj, calcular razones de crecimiento, y explicar por qué un algoritmo
   falla con cierto patron de datos.
2. **El error o supuesto que más me enseño:**
   Pensar que ordenar por otro criterio no afectaba la búsqueda por timestamp
   (mi predicción A), y dar por hecho que QuickSort siempre es rápido.
3. **La pregunta que llevaría a la proxima clase:**
   Si el sistema necesita varios criterios de orden con muchos datos, cuando
   conviene mantener índices separados en vez de ordenar copias?
4. **Que parte del trabajo fue realmente mia:**
   Integre los archivos del profe al proyecto, resolvi que `BancoDePruebas` no
   aparecia porque la rama de la Semana 3 no estaba unida a `main`, ejecute los
   cinco experimentos y registre las mediciones. Para el codigo de los "TODO",
   las decisiones y el borrador de esta bitacora usé ayuda de un asistente de
   IA (Claude). [Ajusta esto a la politica de uso de IA de tu curso.]

## Lista de verificación antes de entregar

- [ ] Escribí la predicción antes de consultar el resultado. (Solo el Exp. 5; aclararlo.)
- [x] Incluí evidencia concreta del laboratorio.
- [x] Explique un concepto sin depender de jerga.
- [x] Registre un vacío, una duda o un error real.
- [x] Trace al menos un caso paso a paso.
- [x] Justifique una decision del proyecto y una alternativa descartada.
- [X] Registre mis commits y mi aporte individual.
- [x] Deje claro que queda pendiente.
- [X] Renombre el archivo con el formato `sXX-nombre.md`.