package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public class GestorEstadoSolicitud {
    private final Solicitud solicitud;
    private EstadoSolicitud estado;

    public GestorEstadoSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        this.estado = crearEstado(solicitud.getEstado());
    }

    public String aprobar() { return ejecutar(s -> s.aprobar(solicitud)); }
    public String rechazar() { return ejecutar(s -> s.rechazar(solicitud)); }
    public String ejecutar() { return ejecutar(s -> s.ejecutar(solicitud)); }
    public String cancelar() { return ejecutar(s -> s.cancelar(solicitud)); }

    private String ejecutar(java.util.function.Function<EstadoSolicitud, String> accion) {
        String estadoAnterior = estado.nombre();
        String resultado = accion.apply(estado);
        if (!estadoAnterior.equals(solicitud.getEstado())) {
            estado = crearEstado(solicitud.getEstado());
        }
        return resultado;
    }

    private EstadoSolicitud crearEstado(String nombre) {
        return switch (nombre) {
            case "PENDIENTE" -> new EstadoPendiente();
            case "APROBADA" -> new EstadoAprobada();
            case "EJECUTADA" -> new EstadoEjecutada();
            case "RECHAZADA" -> new EstadoRechazada();
            case "CANCELADA" -> new EstadoCancelada();
            default -> throw new IllegalArgumentException("Estado no soportado: " + nombre);
        };
    }

    public EstadoSolicitud getEstado() { return estado; }
}
