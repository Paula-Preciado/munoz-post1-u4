package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public interface EstadoSolicitud {
    String aprobar(Solicitud solicitud);
    String rechazar(Solicitud solicitud);
    String ejecutar(Solicitud solicitud);
    String cancelar(Solicitud solicitud);
    String nombre();
}
