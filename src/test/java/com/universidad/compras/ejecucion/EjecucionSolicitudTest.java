package com.universidad.compras.ejecucion;

import com.universidad.compras.modelo.Solicitud;
import com.universidad.compras.notificacion.CambioEstadoService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EjecucionSolicitudTest {
    @Test void ejecutarReservaPresupuestoYGeneraOrden() {
        Solicitud s = new Solicitud("S-010","ana@udes.edu.co",3000000,"SOFTWARE","CC-100");
        s.setEstado("APROBADA");
        EjecutorSolicitud e = new EjecutorSolicitud(s, new CambioEstadoService());
        e.ejecutar(new ReservaPresupuestoOperacion(s, new PresupuestoService()));
        e.ejecutar(new GenerarOrdenOperacion(s, "Proveedor UDES", new OrdenCompraService()));
        assertEquals("EJECUTADA", s.getEstado());
        assertEquals(2, e.getHistorial().size());
    }

    @Test void deshacerSoloLaUltimaOperacionNoAfectaLaAnterior() {
        Solicitud s = new Solicitud("S-011","luis@udes.edu.co",4000000,"MATERIAL_OFICINA","CC-200");
        s.setEstado("APROBADA");
        EjecutorSolicitud e = new EjecutorSolicitud(s, new CambioEstadoService());
        e.ejecutar(new ReservaPresupuestoOperacion(s, new PresupuestoService()));
        GenerarOrdenOperacion orden = new GenerarOrdenOperacion(s, "Proveedor UDES", new OrdenCompraService());
        e.ejecutar(orden);
        e.deshacerUltima();
        assertEquals(1, e.getHistorial().size());
    }

    @Test void elHistorialConservaTodasLasOperacionesNoSoloLaUltima() {
        Solicitud s = new Solicitud("S-012","ana@udes.edu.co",1000000,"SOFTWARE","CC-100");
        s.setEstado("APROBADA");
        EjecutorSolicitud e = new EjecutorSolicitud(s, new CambioEstadoService());
        e.ejecutar(new ReservaPresupuestoOperacion(s, new PresupuestoService()));
        e.ejecutar(new GenerarOrdenOperacion(s, "Proveedor UDES", new OrdenCompraService()));
        assertEquals(2, e.getHistorial().size());
    }
}
