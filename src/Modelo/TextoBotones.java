package Modelo;

import java.util.HashMap;
import java.util.Map;

public class TextoBotones {
    private Map<String, String[]> textosAlternativos;

    public TextoBotones() {
        textosAlternativos = new HashMap<>();
        
        // Textos para ESTUDIANTE
        cargarTextosEstudiante();
        
        // Textos para ADMIN
        cargarTextosAdmin();
    }

    private void cargarTextosEstudiante() {
        textosAlternativos.put("btnDashboard", new String[]{"Inicio", "Ñawpa"});
        textosAlternativos.put("btnCuenta", new String[]{"Cuenta", "Kunta"});
        textosAlternativos.put("btnCertificado", new String[]{"Certificado", "Sertifikatu"});
        textosAlternativos.put("btnJuegos", new String[]{"Juegos", "Pukllaykuna"});
        textosAlternativos.put("btnSalir", new String[]{"Salir", "Llukshina"});
    }

    private void cargarTextosAdmin() {
        textosAlternativos.put("btnMenu1", new String[]{"Inicio", "Ñawpa"});
        textosAlternativos.put("btnCuenta1", new String[]{"Cuenta", "Kunta"});
        textosAlternativos.put("btnGraficos1", new String[]{"Gráficos", "ShuyoKuna"});
        textosAlternativos.put("btnCrearAdmin", new String[]{"Registrar", "Killkakuna"});
        textosAlternativos.put("btnReportes", new String[]{"Reportes", "Willaykuna"});
        textosAlternativos.put("btnSalir1", new String[]{"Salir", "Llukshina"});
    }

    public String[] getTextos(String nombreBoton) {
        return textosAlternativos.get(nombreBoton);
    }
}