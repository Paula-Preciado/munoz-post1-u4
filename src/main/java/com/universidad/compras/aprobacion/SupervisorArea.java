package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class SupervisorArea extends NivelAprobacionBase {
    private static final double LIMITE = 2_000_000;

    @Override
    public ResultadoAprobacion procesar(Solicitud solicitud) {
        return solicitud.getMonto() <= LIMITE
                ? aprobar(solicitud, "Supervisor de Área")
                : delegar(solicitud);
    }
}
