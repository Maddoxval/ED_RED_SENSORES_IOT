# Bitacora individual - Semana [3]

## 1. Datos de la actividad

- **Estudiante:** Maddox Santiago Valbuena Ossa
- **Semana:** 3
- **Fecha del laboratorio:** [2026-09-21]
- **Fecha del taller:** [2026-09-21]
- **Tema principal:** Búsqueda lineal y búsqueda binaria; costo en comparaciones; precondiciones de los algoritmos
- **Pregunta de la semana:** ¿Cómo encontramos una lectura específica cuando el repositorio pasa de cientos a cientos de miles o millones de registros?

## 2. Prediccion antes de ejecutar

Antes de abrir o ejecutar el programa, responde:

1. **Que creo que va a ocurrir?**
  Esperaba que la búsqueda lineal encontrara el dato revisando elemento por elemento, y que fuera lenta   a medida que crece la cantidad de lecturas. Esperaba que la búsqueda binaria fuera mucho más rápida,    porque descarta la mitad de los datos en cada paso, pero solo si el arreglo está ordenado por el        campo  que se busca.
2. **Que parte del programa o del algoritmo puede fallar?**
Tenía mis dudas sobre el uso de == para comparar los Strings en lugar de .equals(), ya que esto evalúa las referencias de los objetos y no su contenido. Asimismo, la búsqueda binaria iba a presentar fallas si el arreglo no se encontraba previamente ordenado por el campo de PM2.5
3. **Como comprobare mi prediccion?** Ejecutando los cuatro experimentos de BancoDePruebas.java con distintos tamaños de datos (1.000, 100.000 y 1.000.000) y comparando el número de comparaciones y el tiempo que toma cada algoritmo.

## 3. Evidencia del laboratorio

### Resultado observado

Al correr IngestaSensores.main(), que ejecuta los cuatro experimentos de la semana, obtuve:

**Experimento 1 — Búsqueda lineal:**
| Lecturas | Comparaciones | Tiempo (ms) |
|---|---|---|
| 1.000 | 1.000 | 1,390 |
| 100.000 |	100.000	| 3,801 |
| 1.000.000	| 1.000.000	| 13,556 |

**Experimento 2 — Lineal vs. Binaria:**
| Lecturas | Lineal | Binaria |
|---|---|---|
| 1.000 | 1.000 | 10 |
| 100.000 | 100.000 | 17 |
| 1.000.000 | 1.000.000 | 20 |

**Experimento 3 — Dato inexistente (100.000 lecturas):**
- Lineal → 100.000 comparaciones (peor caso)
- Binaria → 17 comparaciones

**Experimento 4 — Binaria por PM2.5 (arreglo sin ordenar por ese campo):**
- Valores buscados que sí existen: 20
- Encontrados por búsqueda lineal: 20/20
- Encontrados por búsqueda binaria: 0/20

### Diferencia entre la prediccion y el resultado
La predicción se cumplió en líneas generales: la búsqueda lineal creció de forma proporcional al tamaño (O(n)), mientras que la binaria casi no creció (O(log n)), llegando a ser 50.000 veces más eficiente en comparaciones con 1.000.000 de datos. Lo que más interesante fue la magnitud del Experimento 4: no esperaba que la búsqueda binaria fallara en el 100% de los casos (0 de 20 encontrados) cuando el arreglo no está ordenado por el campo buscado. Pensé que fallaría en algunos casos, no en todos.

### Error o comportamiento inesperado

- **Que ocurrió?** - En el Experimento 4, la búsqueda binaria por PM2.5 no encontró ningún valor, a pesar de que los 20 valores buscados sí estaban presentes en el arreglo, mientras que la búsqueda lineal sí los encontró todos).
- **Por que ocurrió?** - Porque GeneradorDatos genera el PM2.5 con un valor aleatorio (5 + azar.nextDouble() * 55), así que el arreglo no queda ordenado por ese campo. La búsqueda binaria tiene como precondición que los datos estén ordenados ascendentemente según el campo por el que se busca; al no cumplirse esa condición, el algoritmo descarta mitades del arreglo basándose en comparaciones que ya no tienen sentido, y termina "saltándose" la posición real del dato sin encontrarlo.
- **Como lo corregimos o que falta corregir?** - Por ahora no se corrigió: se documentó como una limitación conocida en docs/decisiones.md. La búsqueda binaria por PM2.5 se conserva únicamente como experimento demostrativo de qué pasa cuando se incumple una precondición. Para usarla de forma confiable, habría que ordenar el arreglo por PM2.5 antes de buscar (tarea que queda pendiente para la Semana 4).
Un segundo error, fue el de comparar String con == en el método buscarPorEstacionDefectuoso. Ocurrió porque == compara si dos referencias apuntan al mismo objeto en memoria, no si el contenido de los textos es igual. Se corrigió reemplazando ese método completo por buscarPorEstacion, que usa .equals() en vez de ==.

## 4. Explicacion en lenguaje llano

Imagina que tienes una fila de 100 lockers numerados, y en cada uno hay un papelito con un número. Si buscas un papelito revisando locker por locker desde el 1, en el peor de los casos vas a tener que abrir los 100. Pero si los papelitos ya están ordenados de menor a mayor, puedes abrir directamente el del medio: si el número que buscas es más grande, sigues buscando solo en la mitad de arriba; si es más pequeño, sigues solo en la mitad de abajo. Así, en vez de abrir 100 lockers, abres por mucho 7. Eso es búsqueda binaria: solo funciona si los lockers ya están ordenados; si no lo están, el truco de ir a la mitad deja de servir y puedes no encontrar el papelito aunque esté ahí.

### Ejemplo o analogia

Es como buscar tu asiento en un estadio de fútbol numerado. Si no supieras que las sillas están numeradas en orden, tendrías que caminar puesto por puesto desde la entrada hasta encontrar el tuyo. Pero como sabes que están en orden, haces otra cosa: vas a la mitad de la tribuna, miras el número que hay ahí, y si el tuyo es mayor sigues buscando solo hacia la derecha; si es menor, sigues solo hacia la izquierda. Así, en pocos pasos llegas a tu silla exacta, en vez de recorrer todo el estadio. Esa analogía deja de funcionar si un día reorganizan las sillas por orden de llegada de la gente, en lugar de por número. Ahí ya no sirve el truco de ir a la mitad, porque el orden de las sillas ya no tiene nada que ver tu número de silla, es lo mismo que le pasó a la búsqueda binaria con PM2.5: el algoritmo asumía un orden que en realidad no existía.

## 5. El vacio que encontre

Al intentar explicar el tema, identifica el punto que aun no comprendes bien.

- **Mi duda concreta es:** ¿Por qué la búsqueda binaria no lanza ningún error o aviso cuando el arreglo no está ordenado?
- **Lo que ya puedo explicar es:** por qué la búsqueda binaria es mucho más rápida que la lineal cuando el arreglo sí está ordenado, y por qué hay que usar .equals() en vez de == para comparar el contenido de dos String.
- **Para resolver la duda consulte:** la explicación del punto 32 de la guía sobre por qué el PM2.5 se genera con un valor aleatorio y no queda ordenado. Además le pregunte a Claude para afianzar el concepto.
- **Ahora lo entiendo así:** la búsqueda binaria no verifica su propia precondición porque eso agregaría trabajo extra, donde tiene que recorrer todo el arreglo para chequear que esté ordenado le haría perder la ventaja de velocidad que tiene. El algoritmo confía en que quien lo llama ya garantizó el orden. Por eso es tan importante documentar las precondiciones: si no se cumplen, el algoritmo no avisa, simplemente da respuestas equivocadas

## 6. Trazado de la solucion

Trazado de busquedaBinariaPorTimestamp buscando el timestamp que está en la posición 7 de un arreglo de 8 elementos ordenados

| Paso | Estado de los datos o estructura | Decisión o resultado |
|---|---|---|
| 1 | inicio = 0, fin = 7 | medio = (0+7)/2 = 3. Timestamp en posición 3 es menor al buscado por lo que se descarta la mitad izquierda |
| 2 | inicio = 4, fin = 7 | medio = (4+7)/2 = 5. Timestamp en posición 5 es menor al buscado por lo que se descarta la mitad izquierda |
| 3	| inicio = 6, fin = 7 | medio = (6+7)/2 = 6. Timestamp en posición 6 es menor al buscado por lo q se descarta |
| 4	| inicio = 7, fin = 7 | medio = (7+7)/2 = 7. Timestamp en posición 7 coincide al buscado por lo q devuelve la posición 7 |

**Completa o agrega filas si es necesario.** Si trabajaste con una estructura, dibuja su estado en cada paso o inserta aqui una imagen legible.

## 7. Decision de diseño

Relaciona lo aprendido con la Plataforma de Monitoreo Ambiental Urbano.

- **Problema que debiamos resolver:** [Situacion concreta del sistema.]
- **Estructura, algoritmo o estrategia elegida:** [Nombre y uso.]
- **Alternativa descartada:** [Otra opción razonable.]
- **Por que elegimos la primera:** [Ventaja y costo de la decisión.]
- **Que evidencia respalda la decisión:** [Prueba, medición o comportamiento observado.]

## 8. Aporte al proyecto

- **Archivo(s) o modulo(s) trabajado(s):** [Rutas dentro del repositorio.]
- **Cambio realizado:** [Describe la funcionalidad agregada o modificada.]
- **Como se conecta con la capa anterior:** [Explica la integración.]
- **Que queda pendiente para la siguiente semana:** [Tarea concreta.]

## 9. Commits realizados

Registra los commits que muestran tu aporte individual.

| Commit | Mensaje | Que demuestra |
|---|---|----|
| [hash corto] | [mensaje del commit] | [Cambio realizado] |
| [hash corto] | [mensaje del commit]	| [Cambio realizado] |

## 10. Reexplicacion final

Despues del taller, vuelve a responder la pregunta de la semana en cinco lineas o menos. Esta respuesta debe ser mas precisa que la de la seccion 4 y debe incluir la razon de tu decision tecnica.

> [Escribe aqui tu reexplicacion final.]

## 11. Reflexion individual

Responde con honestidad:

1. **Lo que ahora puedo hacer y antes no podia:**
  [Respuesta.]
2. **El error o supuesto que mas me enseño:**
  [Respuesta.]
4. **La pregunta que llevaria a la proxima clase:**
  [Respuesta.]
6. **Que parte del trabajo fue realmente mia:**
  [Respuesta concreta.]

## Lista de verificacion antes de entregar:

- [X] Escribi la prediccion antes de consultar el resultado.
- [X] Inclui evidencia concreta del laboratorio.
- [X] Explique un concepto sin depender de jerga.
- [X] Registre un vacío, una duda o un error real.
- [X] Trace al menos un caso paso a paso.
- [X] Justifique una decision del proyecto y una alternativa descartada.
- [] Registre mis commits y mi aporte individual.
- [X] Deje claro que queda pendiente.
- [X] Renombre el archivo con el formato `sXX-nombre.md`.
