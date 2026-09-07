package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;

@FunctionalInterface
public interface ReaccionEstado {
    void reaccionar(Solicitud solicitud, String estado, String detalle);
}
