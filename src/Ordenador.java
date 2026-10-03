public class Ordenador {

    private static long comparaciones = 0;
    private static long intercambios = 0;

    public static long getComparaciones() { return comparaciones; }
    public static long getIntercambios()  { return intercambios; }
    public static void reiniciarContadores() { comparaciones = 0; intercambios = 0; }

    // =========================================================
    //  UTILIDADES
    // =========================================================

    private static void intercambiar(LecturaSensor[] datos, int i, int j) {
        LecturaSensor temporal = datos[i];
        datos[i] = datos[j];
        datos[j] = temporal;
        intercambios++;
    }

    /** Compara dos lecturas por su timestamp. */
    private static int comparar(LecturaSensor a, LecturaSensor b) {
        comparaciones++;
        return a.getTimestamp().compareTo(b.getTimestamp());
    }

    /** Compara dos lecturas por su valor de PM2.5. */
    private static int compararPorPm25(LecturaSensor a, LecturaSensor b) {
        comparaciones++;
        return Double.compare(a.getPm25(), b.getPm25());
    }

    // =========================================================
    //  2.7  ALGORITMOS SIMPLES
    // =========================================================

    /**
     * Ordenamiento burbuja con corte temprano.
     *
     * TODO 1 (RESUELTO): se agrego la bandera huboIntercambio. Si en una
     * pasada completa no hubo ningun intercambio, el arreglo ya esta
     * ordenado y se corta. Con datos ya ordenados baja de 49.995.000 a
     * 9.999 comparaciones (n = 10.000).
     */
    public static void burbuja(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = 0; i < n - 1; i++) {
            boolean huboIntercambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparar(datos[j], datos[j + 1]) > 0) {
                    intercambiar(datos, j, j + 1);
                    huboIntercambio = true;
                }
            }
            if (!huboIntercambio) break;
        }
    }

    /**
     * Ordenamiento por seleccion. Busca el menor y lo pone al inicio.
     * Este metodo esta correcto. Observa cuantos intercambios hace.
     */
    public static void seleccion(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = 0; i < n - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < n; j++) {
                if (comparar(datos[j], datos[menor]) < 0) {
                    menor = j;
                }
            }
            if (menor != i) {
                intercambiar(datos, i, menor);
            }
        }
    }

    /**
     * Ordenamiento por insercion. Inserta cada elemento en su lugar
     * dentro de la parte ya ordenada, como quien organiza cartas.
     * Este metodo esta correcto.
     */
    public static void insercion(LecturaSensor[] datos) {
        reiniciarContadores();
        for (int i = 1; i < datos.length; i++) {
            LecturaSensor actual = datos[i];
            int j = i - 1;
            while (j >= 0 && comparar(datos[j], actual) > 0) {
                datos[j + 1] = datos[j];
                intercambios++;
                j--;
            }
            datos[j + 1] = actual;
        }
    }

    // =========================================================
    //  2.8  ALGORITMOS AVANZADOS
    // =========================================================

    /**
     * MergeSort. Divide el arreglo por la mitad, ordena cada parte
     * y luego fusiona las dos partes ordenadas. Este metodo esta correcto.
     *
     * Fijate en la idea: es la misma de la busqueda binaria, dividir
     * el problema en dos, pero aplicada a ordenar en vez de a buscar.
     */
    public static void mergeSort(LecturaSensor[] datos) {
        reiniciarContadores();
        LecturaSensor[] auxiliar = new LecturaSensor[datos.length];
        mergeSortRecursivo(datos, auxiliar, 0, datos.length - 1);
    }

    private static void mergeSortRecursivo(LecturaSensor[] datos, LecturaSensor[] aux,
                                           int inicio, int fin) {
        if (inicio >= fin) return;
        int medio = inicio + (fin - inicio) / 2;
        mergeSortRecursivo(datos, aux, inicio, medio);
        mergeSortRecursivo(datos, aux, medio + 1, fin);
        fusionar(datos, aux, inicio, medio, fin);
    }

    private static void fusionar(LecturaSensor[] datos, LecturaSensor[] aux,
                                 int inicio, int medio, int fin) {
        for (int i = inicio; i <= fin; i++) aux[i] = datos[i];
        int izq = inicio, der = medio + 1;
        for (int k = inicio; k <= fin; k++) {
            if (izq > medio) {
                datos[k] = aux[der++];
            } else if (der > fin) {
                datos[k] = aux[izq++];
            } else if (comparar(aux[der], aux[izq]) < 0) {
                datos[k] = aux[der++];
            } else {
                datos[k] = aux[izq++];
            }
            intercambios++;
        }
    }

    /**
     * QuickSort con pivote ALEATORIO.
     *
     * TODO 2 (RESUELTO): con el primer elemento como pivote y datos ya
     * ordenados, cada particion dejaba un grupo vacio y el programa
     * moria con StackOverflowError. Ahora el pivote se elige al azar
     * dentro del tramo (ver particionar), asi que ningun patron de
     * entrada, como el orden cronologico, lo degrada.
     *
     * El nombre del metodo se conserva para no romper BancoDeOrdenamiento.
     */
    public static void quickSortPivotePrimero(LecturaSensor[] datos) {
        reiniciarContadores();
        quickRecursivo(datos, 0, datos.length - 1);
    }

    private static void quickRecursivo(LecturaSensor[] datos, int inicio, int fin) {
        if (inicio >= fin) return;
        int posicionPivote = particionar(datos, inicio, fin);
        quickRecursivo(datos, inicio, posicionPivote - 1);
        quickRecursivo(datos, posicionPivote + 1, fin);
    }

    private static int particionar(LecturaSensor[] datos, int inicio, int fin) {
        // Pivote aleatorio: se lleva al inicio del tramo y se particiona como antes.
        int posicionAzar = inicio + (int) (Math.random() * (fin - inicio + 1));
        intercambiar(datos, inicio, posicionAzar);
        LecturaSensor pivote = datos[inicio];
        int limite = inicio;
        for (int i = inicio + 1; i <= fin; i++) {
            if (comparar(datos[i], pivote) < 0) {
                limite++;
                intercambiar(datos, limite, i);
            }
        }
        intercambiar(datos, inicio, limite);
        return limite;
    }

    /**
     * HeapSort. Ordena usando una estructura llamada monticulo.
     * Este metodo esta correcto y funciona como caja negra por ahora:
     * la estructura que usa por dentro la vas a construir en la semana 6.
     */
    public static void heapSort(LecturaSensor[] datos) {
        reiniciarContadores();
        int n = datos.length;
        for (int i = n / 2 - 1; i >= 0; i--) hundir(datos, n, i);
        for (int i = n - 1; i > 0; i--) {
            intercambiar(datos, 0, i);
            hundir(datos, i, 0);
        }
    }

    private static void hundir(LecturaSensor[] datos, int tamano, int raiz) {
        int mayor = raiz;
        int izq = 2 * raiz + 1;
        int der = 2 * raiz + 2;
        if (izq < tamano && comparar(datos[izq], datos[mayor]) > 0) mayor = izq;
        if (der < tamano && comparar(datos[der], datos[mayor]) > 0) mayor = der;
        if (mayor != raiz) {
            intercambiar(datos, raiz, mayor);
            hundir(datos, tamano, mayor);
        }
    }

    // =========================================================
    //  ORDENAR POR OTRO CRITERIO
    // =========================================================

    /**
     * Devuelve una COPIA de las lecturas ordenada por PM2.5, de menor a mayor.
     * Sirve para construir el ranking de estaciones mas contaminadas.
     *
     * TODO 3 (RESUELTO): antes este metodo ordenaba el MISMO arreglo que se
     * consulta por timestamp, y la busqueda binaria dejaba de funcionar
     * porque su precondicion (orden por timestamp) ya no se cumplia.
     * Ahora el original no se toca: se ordena una copia.
     * Costo: memoria adicional y el tiempo de copiar.
     */
    public static LecturaSensor[] ordenarPorPm25(LecturaSensor[] datos) {
        reiniciarContadores();
        LecturaSensor[] copia = new LecturaSensor[datos.length];
        System.arraycopy(datos, 0, copia, 0, datos.length);

        for (int i = 1; i < copia.length; i++) {
            LecturaSensor actual = copia[i];
            int j = i - 1;
            while (j >= 0 && compararPorPm25(copia[j], actual) > 0) {
                copia[j + 1] = copia[j];
                intercambios++;
                j--;
            }
            copia[j + 1] = actual;
        }
        return copia;
    }

    /** Verifica si un arreglo esta ordenado ascendentemente por timestamp. */
    public static boolean estaOrdenadoPorTimestamp(LecturaSensor[] datos) {
        for (int i = 1; i < datos.length; i++) {
            if (datos[i - 1].getTimestamp().compareTo(datos[i].getTimestamp()) > 0) {
                return false;
            }
        }
        return true;
    }
}