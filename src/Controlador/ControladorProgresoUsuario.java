package Controlador;

import Modelo.Modelo_Progreso_Usuario;
import java.time.LocalDateTime;

public class ControladorProgresoUsuario {

    public static Modelo_Progreso_Usuario obtenerProgreso(int idUsuario, int idUnidad) {
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, idUnidad);
        if (progreso == null) {
            progreso = new Modelo_Progreso_Usuario(0, idUsuario, idUnidad, 0, 0, false, 0, LocalDateTime.now());
            Modelo_Progreso_Usuario.guardarProgreso(progreso);
        }
        return progreso;
    }

    public static boolean actualizarLeccion(Modelo_Progreso_Usuario progreso, int nuevaCantidad) {
        if (progreso.getLeccionesCompletadas() < nuevaCantidad) {
            progreso.setLeccionesCompletadas(nuevaCantidad);
            progreso.setFechaActualizacion(LocalDateTime.now());
            return Modelo_Progreso_Usuario.actualizarProgreso(progreso);
        }
        return false;
    }

    public static boolean actualizarActividad(Modelo_Progreso_Usuario progreso, int nuevaCantidad) {
        if (progreso.getActividadesCompletadas() < nuevaCantidad) {
            progreso.setActividadesCompletadas(nuevaCantidad);
            progreso.setFechaActualizacion(LocalDateTime.now());
            return Modelo_Progreso_Usuario.actualizarProgreso(progreso);
        }
        return false;
    }

    public static boolean aprobarEvaluacion(Modelo_Progreso_Usuario progreso, int calificacion) {
        if (!progreso.isEvaluacionAprobada()) {
            progreso.setEvaluacionAprobada(true);
            progreso.setCalificacion(calificacion);
            progreso.setFechaActualizacion(LocalDateTime.now());
            return Modelo_Progreso_Usuario.actualizarProgreso(progreso);
        }
        return false;
    }

    /**
     * Calcula el progreso basado en 7 pasos totales:
     * 3 lecciones + 3 actividades + 1 evaluación = 7 pasos
     * Cada paso = 100/7 = ~14.29%
     */
    public static int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
        final double PROGRESO_POR_PASO = 100.0 / 7.0; // ~14.29% por paso
        
        int pasos = 0;
        
        // Contar lecciones completadas (máximo 3)
        pasos += Math.min(lecciones, 3);
        
        // Contar actividades completadas (máximo 3)
        pasos += Math.min(actividades, 3);
        
        // Contar evaluación (0 o 1)
        if (evaluacion) {
            pasos += 1;
        }
        
        // Calcular progreso total
        int progreso = (int) Math.round(pasos * PROGRESO_POR_PASO);
        
        // Asegurar que no exceda 100%
        return Math.min(progreso, 100);
    }
    
    /**
     * Verifica si se puede acceder al siguiente paso según el progreso actual
     * Orden secuencial ESTRICTO: Fonología → Actividad 1 → Saludos → Actividad 2 → Pronombres → Actividad 3 → Evaluación
     * SOLO se desbloquea la siguiente tarea inmediata, nunca múltiples tareas a la vez
     */
    public static boolean puedeAccederAPaso(Modelo_Progreso_Usuario progreso, TipoPaso tipoPaso, int numeroPaso) {
        int leccionesCompletadas = progreso.getLeccionesCompletadas();
        int actividadesCompletadas = progreso.getActividadesCompletadas();
        boolean evaluacionAprobada = progreso.isEvaluacionAprobada();
        
        switch (tipoPaso) {
            case LECCION:
                // Lección 1 (Fonología): siempre accesible - PASO 1
                if (numeroPaso == 1) return true;
                // Lección 2 (Saludos): requiere Fonología(1) + Actividad 1(1) - PASO 3
                if (numeroPaso == 2) return leccionesCompletadas >= 1 && actividadesCompletadas >= 1;
                // Lección 3 (Pronombres): requiere Saludos(2) + Actividad 2(2) - PASO 5
                if (numeroPaso == 3) return leccionesCompletadas >= 2 && actividadesCompletadas >= 2;
                break;
                
            case ACTIVIDAD:
                // Actividad 1: requiere Fonología(1) completada - PASO 2
                if (numeroPaso == 1) return leccionesCompletadas >= 1;
                // Actividad 2: requiere Saludos(2) completada - PASO 4
                if (numeroPaso == 2) return leccionesCompletadas >= 2 && actividadesCompletadas >= 1;
                // Actividad 3: requiere Pronombres(3) completada - PASO 6
                if (numeroPaso == 3) return leccionesCompletadas >= 3 && actividadesCompletadas >= 2;
                break;
                
            case EVALUACION:
                // Evaluación: requiere EXACTAMENTE todas las lecciones(3) y actividades(3) completadas - PASO 7
                return leccionesCompletadas == 3 && actividadesCompletadas == 3 && !evaluacionAprobada;
        }
        
        return false;
    }
    
    /**
     * Enum para tipos de pasos en la unidad
     */
    public enum TipoPaso {
        LECCION, ACTIVIDAD, EVALUACION
    }
}

