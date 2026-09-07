package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public abstract class NivelAprobacionBase implements NivelAprobacion {
    private NivelAprobacion siguiente;

    @Override
    public void setSiguiente(NivelAprobacion siguiente) {
        this.siguiente = siguiente;
    }

    protected ResultadoAprobacion delegar(Solicitud solicitud) {
        if (siguiente == null) {
            solicitud.setEstado("RECHAZADA");
            solicitud.setNivelResolutor("Sistema de Aprobación");
            return new ResultadoAprobacion(false, "Sistema de Aprobación",
                    "La solicitud no pudo ser resuelta por los niveles disponibles.");
        }
        return siguiente.procesar(solicitud);
    }

    protected ResultadoAprobacion aprobar(Solicitud solicitud, String nombre) {
        solicitud.setEstado("APROBADA");
        solicitud.setNivelResolutor(nombre);
        return new ResultadoAprobacion(true, nombre, "Solicitud aprobada por " + nombre + ".");
    }

    protected ResultadoAprobacion rechazar(Solicitud solicitud, String nombre, String detalle) {
        solicitud.setEstado("RECHAZADA");
        solicitud.setNivelResolutor(nombre);
        return new ResultadoAprobacion(false, nombre, detalle);
    }
}
