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

**Experimento 3 — Dato inexistente (100.000 lecturas):**
- Lineal → 100.000 comparaciones (peor caso)
- Binaria → 17 comparaciones

**Experimento 4 — Binaria por PM2.5 (arreglo sin ordenar por ese campo):**
- Valores buscados que sí existen: 20
- Encontrados por búsqueda lineal: 20/20
- Encontrados por búsqueda binaria: 0/20

### Diferencia entre la prediccion y el resultado

[Explica que coincidencias o diferencias encontraste y que las puede explicar.]

### Error o comportamiento inesperado

- **Que ocurrio?** [Describe el problema sin ocultarlo.]
- **Por que ocurrió?** [Explica la causa con la evidencia disponible.]
- **Como lo corregimos o que falta corregir?** [Describe la solucion o el siguiente paso.]

## 4. Explicacion en lenguaje llano

Explica el concepto principal como se lo explicarias a una persona de doce anos. Usa entre tres y cinco lineas y evita palabras tecnicas que no expliques.

> [Escribe aqui tu explicacion.]

### Ejemplo o analogia

[Relaciona el concepto con una situacion cotidiana. Explica que representa cada parte de la analogia y donde deja de ser exacta.]

## 5. El vacio que encontre

Al intentar explicar el tema, identifica el punto que aun no comprendes bien.

- **Mi duda concreta es:** [Pregunta especifica, no "no entiendo nada".]
- **Lo que ya puedo explicar es:** [Parte que si comprendes.]
- **Para resolver la duda consulte:** [Clase, lectura, experimento, companero u otra fuente.]
- **Ahora lo entiendo asi:** [Respuesta escrita con tus palabras.]

## 6. Trazado de la solucion

Escoge una ejecucion, recorrido o caso representativo y trazalo paso a paso. Incluye los valores importantes despues de cada paso.

| Paso | Estado de los datos o estructura | Decisión o resultado |
|---|---|---|
| 1 | [Estado inicial] | [Que ocurre] |
| 2 | [Siguiente estado] | [Que ocurre] |
| 3	| [Siguiente estado] | [Que ocurre] |
| 4	| [Estado final] | [Que ocurre] |

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
