package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;

public class GenerarOrdenOperacion implements Operacion {
    private final Solicitud solicitud;
    private final String proveedor;
    private final OrdenCompraService servicio;
    private String numeroOrden;

    public GenerarOrdenOperacion(Solicitud solicitud, String proveedor, OrdenCompraService servicio) {
        this.solicitud = solicitud;
        this.proveedor = proveedor;
        this.servicio = servicio;
    }

    @Override
    public void ejecutar() {
        numeroOrden = servicio.generar(solicitud.getId(), proveedor);
    }

    @Override
    public void deshacer() {
        if (numeroOrden != null) {
            servicio.cancelar(numeroOrden);
            numeroOrden = null;
        }
    }

    @Override
    public String getDescripcion() { return "Generación de orden de compra"; }
}
