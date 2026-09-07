package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;

public class DirectorFinanciero extends NivelAprobacionBase {
    @Override
    public ResultadoAprobacion procesar(Solicitud solicitud) {
        return aprobar(solicitud, "Director Financiero");
    }
}
