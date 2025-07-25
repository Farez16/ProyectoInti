package Controlador;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor global de controladores de video para evitar audio en segundo plano
 */
public class VideoManager {
    
    private static VideoManager instance;
    private List<Controlador_Video> controladoresActivos;
    
    private VideoManager() {
        controladoresActivos = new ArrayList<>();
    }
    
    public static VideoManager getInstance() {
        if (instance == null) {
            instance = new VideoManager();
        }
        return instance;
    }
    
    /**
     * Registra un controlador de video activo
     */
    public void registrarControlador(Controlador_Video controlador) {
        if (controlador != null && !controladoresActivos.contains(controlador)) {
            controladoresActivos.add(controlador);
            System.out.println("Controlador de video registrado. Total activos: " + controladoresActivos.size());
        }
    }
    
    /**
     * Desregistra un controlador de video
     */
    public void desregistrarControlador(Controlador_Video controlador) {
        if (controlador != null) {
            controladoresActivos.remove(controlador);
            System.out.println("Controlador de video desregistrado. Total activos: " + controladoresActivos.size());
        }
    }
    
    /**
     * Detiene y limpia todos los controladores de video activos
     */
    public void detenerTodosLosVideos() {
        System.out.println("Deteniendo todos los videos activos (" + controladoresActivos.size() + ")");
        
        List<Controlador_Video> copia = new ArrayList<>(controladoresActivos);
        for (Controlador_Video controlador : copia) {
            try {
                if (controlador != null) {
                    controlador.detenerYLiberarRecursos();
                }
            } catch (Exception e) {
                System.err.println("Error al detener controlador de video: " + e.getMessage());
            }
        }
        
        // Limpiar la lista después de detener todos
        controladoresActivos.clear();
        System.out.println("Todos los videos han sido detenidos y limpiados");
    }
    
    /**
     * Obtiene el número de controladores activos
     */
    public int getNumeroControladoresActivos() {
        return controladoresActivos.size();
    }
    
    /**
     * Limpia controladores nulos o inválidos de la lista
     */
    public void limpiarControladoresInvalidos() {
        controladoresActivos.removeIf(controlador -> controlador == null);
    }
}
