package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionEstadoTest {
    @Test void cambiarEstadoDisparaLasTresReaccionesSinLanzarExcepcion() {
        Solicitud s = new Solicitud("S-020","ana@udes.edu.co",2500000,"SOFTWARE","CC-100");
        CambioEstadoService mecanismo = new CambioEstadoService();
        assertDoesNotThrow(() -> mecanismo.cambiarEstado(s,"APROBADA","Prueba"));
        assertEquals("APROBADA", s.getEstado());
    }

    @Test void agregarUnCuartoSuscriptorDePruebaNoRequiereModificarElMecanismo() {
        Solicitud s = new Solicitud("S-021","ana@udes.edu.co",2500000,"SOFTWARE","CC-100");
        CambioEstadoService mecanismo = new CambioEstadoService();
        AtomicBoolean recibido = new AtomicBoolean(false);
        mecanismo.suscribir((solicitud, estado, detalle) -> recibido.set(true));
        mecanismo.cambiarEstado(s,"APROBADA","Cuarto suscriptor");
        assertTrue(recibido.get());
    }
}
