package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class RevisorCumplimiento extends NivelAprobacionBase {
    @Override
    public ResultadoAprobacion procesar(Solicitud solicitud) {
        // El revisor especial verifica la categoría y, para este laboratorio,
        // remite la solicitud al nivel por monto sin resolverla.
        return delegar(solicitud);
    }
}
