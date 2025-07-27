package Modelo;

import java.util.HashMap;
import java.util.Map;

public class TextoBotones {

    private Map<String, String[]> textosAlternativos;

    public TextoBotones() {
        textosAlternativos = new HashMap<>();
        
        // Textos para botones de ESTUDIANTE (Dashboard)
        textosAlternativos.put("btnDashboard", new String[]{"Inicio", "Ñawpa"});
        textosAlternativos.put("btnCuenta", new String[]{"Cuenta", "Kunta"});
        textosAlternativos.put("btnCertificado", new String[]{"Certificado", "Sertifikatu"});
        textosAlternativos.put("btnJuegos", new String[]{"Juegos", "Pukllaykuna "});
        textosAlternativos.put("btnSalir", new String[]{"Salir", "Llukshina"});

        // Textos para botones de ADMIN (DashboardAdmin)
        textosAlternativos.put("btnMenu1", new String[]{"Inicio", "Ñawpa"});
        textosAlternativos.put("btnCuenta1", new String[]{"Cuenta", "Kunta"});
        textosAlternativos.put("btnGraficos1", new String[]{"Graficos", "ShuyoKuna"});
        textosAlternativos.put("btnCrearAdmin", new String[]{"Registrar", "Killkakuna"});
        textosAlternativos.put("btnSalir1", new String[]{"Salir", "Llukshina"});
        textosAlternativos.put("btnReportes", new String []{"Reportes", "Willaykuna"});
    }

    public String[] getTextos(String nombreBoton) {
        return textosAlternativos.get(nombreBoton);
    }
}
