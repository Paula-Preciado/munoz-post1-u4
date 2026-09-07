package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class GerenteArea extends NivelAprobacionBase {
    private static final double LIMITE = 10_000_000;

    @Override
    public ResultadoAprobacion procesar(Solicitud solicitud) {
        return solicitud.getMonto() <= LIMITE
                ? aprobar(solicitud, "Gerente de Área")
                : delegar(solicitud);
    }
}
