package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public class EstadoAprobada extends EstadoBase {
    @Override public String ejecutar(Solicitud s) { s.setEstado("EJECUTADA"); return "Ejecutada"; }
    @Override public String cancelar(Solicitud s) { s.setEstado("CANCELADA"); return "Cancelada"; }
    @Override public String nombre() { return "APROBADA"; }
}
