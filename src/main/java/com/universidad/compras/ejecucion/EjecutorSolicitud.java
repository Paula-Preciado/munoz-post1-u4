package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;
import com.universidad.compras.notificacion.CambioEstadoService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EjecutorSolicitud {
    private final Solicitud solicitud;
    private final CambioEstadoService cambioEstadoService;
    private final List<Operacion> historial = new ArrayList<>();

    public EjecutorSolicitud(Solicitud solicitud, CambioEstadoService cambioEstadoService) {
        this.solicitud = solicitud;
        this.cambioEstadoService = cambioEstadoService;
    }

    public void ejecutar(Operacion operacion) {
        operacion.ejecutar();
        historial.add(operacion);
        solicitud.setEstado("EJECUTADA");
        cambioEstadoService.cambiarEstado(solicitud, "EJECUTADA",
                "Operación ejecutada: " + operacion.getDescripcion());
    }

    public void deshacerUltima() {
        if (!historial.isEmpty()) {
            Operacion ultima = historial.get(historial.size() - 1);
            ultima.deshacer();
            historial.remove(historial.size() - 1);
            if (historial.isEmpty()) {
                solicitud.setEstado("APROBADA");
                cambioEstadoService.cambiarEstado(solicitud, "APROBADA",
                        "Se deshizo la última operación ejecutada.");
            }
        }
    }

    public List<Operacion> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public void deshacer(Operacion operacion) {
        if (historial.remove(operacion)) {
            operacion.deshacer();
            if (historial.isEmpty()) solicitud.setEstado("APROBADA");
        }
    }
}
