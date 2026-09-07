package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public abstract class EstadoBase implements EstadoSolicitud {
    protected String rechazarOperacion(String operacion, Solicitud s) {
        return "Error: no se puede " + operacion + " una solicitud en estado " + s.getEstado();
    }
    protected String permitido(String mensaje) { return mensaje; }
    @Override public String aprobar(Solicitud s) { return rechazarOperacion("aprobar", s); }
    @Override public String rechazar(Solicitud s) { return rechazarOperacion("rechazar", s); }
    @Override public String ejecutar(Solicitud s) { return rechazarOperacion("ejecutar", s); }
    @Override public String cancelar(Solicitud s) { return rechazarOperacion("cancelar", s); }
}
