package com.universidad.compras.aprobacion;

import com.universidad.compras.modelo.Solicitud;
import com.universidad.compras.notificacion.CambioEstadoService;
import org.springframework.stereotype.Service;

@Service
public class AprobacionService implements ServicioAprobacion {
    private final CambioEstadoService cambioEstadoService;

    public AprobacionService(CambioEstadoService cambioEstadoService) {
        this.cambioEstadoService = cambioEstadoService;
    }

    @Override
    public ResultadoAprobacion evaluar(Solicitud solicitud) {
        String estadoAnterior = solicitud.getEstado();

        NivelAprobacion supervisor = new SupervisorArea();
        NivelAprobacion gerente = new GerenteArea();
        NivelAprobacion director = new DirectorFinanciero();

        supervisor.setSiguiente(gerente);
        gerente.setSiguiente(director);

        if ("INTERNACIONAL".equalsIgnoreCase(solicitud.getCategoria())) {
            NivelAprobacion cumplimiento = new RevisorCumplimiento();
            cumplimiento.setSiguiente(supervisor);
            ResultadoAprobacion resultado = cumplimiento.procesar(solicitud);
            notificarSiCambio(solicitud, estadoAnterior, resultado.getDetalle());
            return resultado;
        }

        ResultadoAprobacion resultado = supervisor.procesar(solicitud);
        notificarSiCambio(solicitud, estadoAnterior, resultado.getDetalle());
        return resultado;
    }

    private void notificarSiCambio(Solicitud solicitud, String anterior, String detalle) {
        if (!anterior.equals(solicitud.getEstado())) {
            cambioEstadoService.cambiarEstado(solicitud, solicitud.getEstado(), detalle);
        }
    }
}
