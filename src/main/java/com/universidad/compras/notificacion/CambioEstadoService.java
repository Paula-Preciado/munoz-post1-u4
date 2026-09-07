package com.universidad.compras.notificacion;

import com.universidad.compras.modelo.Solicitud;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public class CambioEstadoService {
    private final List<ReaccionEstado> suscriptores = new CopyOnWriteArrayList<>();

    public CambioEstadoService() {
        suscriptores.add((s, estado, detalle) ->
                ClientesNotificacion.enviarCorreo(s.getSolicitanteEmail(),
                        "Cambio de estado de solicitud " + s.getId(),
                        "La solicitud cambió a " + estado + "."));
        suscriptores.add((s, estado, detalle) ->
                ClientesNotificacion.actualizarDashboardContabilidad(s.getId(), estado, s.getMonto()));
        suscriptores.add((s, estado, detalle) ->
                ClientesNotificacion.registrarAuditoria(s.getId(), estado, detalle));
    }

    public void suscribir(ReaccionEstado reaccion) {
        suscriptores.add(reaccion);
    }

    public void cambiarEstado(Solicitud solicitud, String nuevoEstado, String detalle) {
        solicitud.setEstado(nuevoEstado);
        suscriptores.forEach(r -> r.reaccionar(solicitud, nuevoEstado, detalle));
    }
}
