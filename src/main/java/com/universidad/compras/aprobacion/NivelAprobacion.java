package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public interface NivelAprobacion {
    void setSiguiente(NivelAprobacion siguiente);
    ResultadoAprobacion procesar(Solicitud solicitud);
}
