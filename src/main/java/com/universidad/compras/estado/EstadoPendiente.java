package com.universidad.compras.estado;

import com.universidad.compras.modelo.Solicitud;

public class EstadoPendiente extends EstadoBase {
    @Override public String aprobar(Solicitud s) { s.setEstado("APROBADA"); return "Aprobada"; }
    @Override public String rechazar(Solicitud s) { s.setEstado("RECHAZADA"); return "Rechazada"; }
    @Override public String nombre() { return "PENDIENTE"; }
}
