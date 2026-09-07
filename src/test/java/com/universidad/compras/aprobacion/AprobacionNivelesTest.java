package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;
import com.universidad.compras.notificacion.CambioEstadoService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AprobacionNivelesTest {
    private ServicioAprobacion servicio() {
        return new AprobacionService(new CambioEstadoService());
    }

    @Test void solicitudDentroDeAutoridadDelSupervisorSeAprueba() {
        ResultadoAprobacion r = servicio().evaluar(
                new Solicitud("S-001","ana@udes.edu.co",1500000,"MATERIAL_OFICINA","CC-100"));
        assertTrue(r.isAprobada());
        assertEquals("Supervisor de Área", r.getNivelResolutor());
    }

    @Test void solicitudQueSuperaAlSupervisorEscalaAlGerente() {
        ResultadoAprobacion r = servicio().evaluar(
                new Solicitud("S-002","luis@udes.edu.co",6000000,"SOFTWARE","CC-200"));
        assertTrue(r.isAprobada());
        assertEquals("Gerente de Área", r.getNivelResolutor());
    }

    @Test void solicitudInternacionalPasaPorCumplimientoAntesDelNivelPorMonto() {
        ResultadoAprobacion r = servicio().evaluar(
                new Solicitud("S-003","gerencia@udes.edu.co",1000000,"INTERNACIONAL","CC-300"));
        assertEquals("Supervisor de Área", r.getNivelResolutor());
    }

    @Test void solicitudSuperiorAlGerenteLlegaAlDirector() {
        ResultadoAprobacion r = servicio().evaluar(
                new Solicitud("S-004","gerencia@udes.edu.co",12000000,"SOFTWARE","CC-300"));
        assertEquals("Director Financiero", r.getNivelResolutor());
    }
}
