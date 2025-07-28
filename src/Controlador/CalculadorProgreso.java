package Controlador;

/**
 * Clase utilitaria para centralizar el cálculo de progreso de las unidades
 * Evita duplicación de código y asegura consistencia en toda la aplicación
 */
public class CalculadorProgreso {
    
    /**
     * Calcula el progreso de una unidad basado en lecciones, actividades y evaluación completadas
     * 
     * Flujo de progreso para Unidad 1:
     * - Fonología (lección 1): 15%
     * - Actividad 1: 30%
     * - Saludos (lección 2): 45%
     * - Actividad 2: 60%
     * - Pronombres (lección 3): 75%
     * - Actividad 3: 90%
     * - Evaluación: 100%
     * 
     * @param lecciones Número de lecciones completadas
     * @param actividades Número de actividades completadas
     * @param evaluacion Si la evaluación está completada
     * @return Progreso de la unidad (0-100)
     */
    public static int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
        int progreso = 0;

        // Nuevo flujo: fonología(15), actividad1(30), saludos(45), actividad2(60), pronombres(75), actividad3(90), evaluación(100)
        if (lecciones >= 1) {  // Primera lección completada (ej: Fonología)
            progreso = 15;
        }
        if (actividades >= 1) {  // Primera actividad completada
            progreso = 30;
        }
        if (lecciones >= 2) {  // Segunda lección completada (ej: Saludos)
            progreso = 45;
        }
        if (actividades >= 2) {  // Segunda actividad completada
            progreso = 60;
        }
        if (lecciones >= 3) {  // Tercera lección completada (ej: Pronombres)
            progreso = 75;
        }
        if (actividades >= 3) {  // Tercera actividad completada
            progreso = 90;
        }
        if (evaluacion) {  // Evaluación completada
            progreso = 100;
        }

        return progreso;
    }
    
    /**
     * Calcula el progreso total del sistema basado en unidades completadas
     * Solo cuenta unidades que están al 100% completadas
     * Cada unidad completada contribuye con 25% al total
     * 
     * @param progresosUnidades Array con el progreso de cada unidad (0-100)
     * @return Progreso total del sistema (0, 25, 50, 75, 100)
     */
    public static int calcularProgresoTotal(int[] progresosUnidades) {
        int unidadesCompletadas = 0;
        
        for (int i = 0; i < progresosUnidades.length; i++) {
            if (progresosUnidades[i] >= 100) {
                unidadesCompletadas++;
                System.out.println("Unidad " + (i + 1) + ": COMPLETADA (" + progresosUnidades[i] + "%)");
            } else {
                System.out.println("Unidad " + (i + 1) + ": EN PROGRESO (" + progresosUnidades[i] + "%) - No contribuye al total");
            }
        }
        
        // Cada unidad completada contribuye con 25%
        int progresoTotal = unidadesCompletadas * 25;
        
        // Limitar el progreso total a 100% (máximo 4 unidades)
        int progresoFinal = Math.min(progresoTotal, 100);
        System.out.println("Unidades completadas: " + unidadesCompletadas + "/" + progresosUnidades.length + " -> Progreso total: " + progresoFinal + "%");
        
        return progresoFinal;
    }
}
