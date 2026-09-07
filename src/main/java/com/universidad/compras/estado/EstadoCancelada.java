package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public class EstadoCancelada extends EstadoBase {
    @Override public String nombre() { return "CANCELADA"; }
}
