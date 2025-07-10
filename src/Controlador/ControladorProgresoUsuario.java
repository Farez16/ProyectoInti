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

    public static int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
        int progreso = 0;
        if (lecciones >= 1) progreso = 15;
        if (lecciones >= 2) progreso = 30;
        if (lecciones >= 3) progreso = 45;
        if (actividades >= 1) progreso = 60;
        if (actividades >= 2) progreso = 75;
        if (evaluacion) progreso = 100;
        return progreso;
    }
}

