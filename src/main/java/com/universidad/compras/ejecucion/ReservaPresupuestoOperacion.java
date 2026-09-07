package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;

public class ReservaPresupuestoOperacion implements Operacion {
    private final Solicitud solicitud;
    private final PresupuestoService servicio;
    private boolean ejecutada;

    public ReservaPresupuestoOperacion(Solicitud solicitud, PresupuestoService servicio) {
        this.solicitud = solicitud;
        this.servicio = servicio;
    }

    @Override
    public void ejecutar() {
        if (servicio.reservar(solicitud.getCentroCosto(), solicitud.getMonto())) ejecutada = true;
    }

    @Override
    public void deshacer() {
        if (ejecutada) {
            servicio.liberar(solicitud.getCentroCosto(), solicitud.getMonto());
            ejecutada = false;
        }
    }

    @Override
    public String getDescripcion() { return "Reserva de presupuesto"; }
}
