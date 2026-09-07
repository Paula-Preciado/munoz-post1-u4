# Post-contenido — Unidad 4: Patrones de Comportamiento en ComprasUDES

## Descripción
Proyecto Spring Boot para resolver cuatro necesidades del backend de ComprasUDES: aprobación por niveles jerárquicos, ejecución reversible con historial, notificaciones desacopladas y reglas de transición según el estado actual.

## Cómo ejecutar
Requisitos: Java 17+, Maven 3.8+ y Git.

```bash
mvn clean package
mvn test
mvn spring-boot:run
```

Endpoint de evaluación:
`POST /api/solicitudes/evaluar`

Ejemplo:
```json
{
  "id": "S-001",
  "solicitanteEmail": "ana@udes.edu.co",
  "monto": 1500000,
  "categoria": "MATERIAL_OFICINA",
  "centroCosto": "CC-100"
}
```

## Estructura
- `modelo/`: entidad compartida.
- `aprobacion/`: cadena de niveles de aprobación.
- `ejecucion/`: operaciones ejecutables, reversibles e historial.
- `notificacion/`: suscriptores de cambios de estado.
- `estado/`: estados y reglas de transición.
- `src/test/`: pruebas JUnit 5.

## Decisiones de diseño

### Necesidad 1 — Aprobación por niveles jerárquicos
**Patrón aplicado: Chain of Responsibility.** La solicitud se entrega a una cadena de decisores. Cada nivel determina si puede resolverla y, si no, la delega al siguiente. La cadena puede crecer, quitar niveles o cambiar el orden sin modificar `ControladorSolicitudes`, que solo conoce el contrato `ServicioAprobacion`. Para `INTERNACIONAL` se inserta `RevisorCumplimiento` antes de la cadena por monto.

**Alternativa descartada: Command.** Command encapsula una operación como un objeto para ejecutarla o deshacerla; no modela naturalmente una petición que avanza entre decisores hasta que uno la resuelve. Esa necesidad de delegación es precisamente la que resuelve Chain of Responsibility.

### Necesidad 2 — Ejecución reversible de solicitudes
**Patrón aplicado: Command.** `ReservaPresupuestoOperacion` y `GenerarOrdenOperacion` encapsulan acciones independientes mediante `Operacion`, cada una con `ejecutar()` y `deshacer()`. `EjecutorSolicitud` conserva todas las operaciones en orden, por lo que puede consultar el historial y revertir una operación sin mezclar la lógica de bajo nivel de `PresupuestoService` y `OrdenCompraService`.

**Alternativa descartada: Chain of Responsibility.** Aquí no existe una cadena de decisores delegando una solicitud. El equipo decide qué operación ejecutar y cada operación necesita una acción reversible e independiente, por lo que Command encaja mejor.

### Necesidad 3 — Notificaciones ante cambio de estado
**Patrón aplicado: Observer.** `CambioEstadoService` mantiene una colección de suscriptores (`ReaccionEstado`). Al cambiar el estado, notifica a todos: correo, dashboard y auditoría. Un cuarto suscriptor puede registrarse mediante `suscribir()` sin modificar el mecanismo central.

**Alternativa descartada: State.** State modela el comportamiento que pertenece al propio objeto según su estado actual. Aquí el objetivo es que módulos externos reaccionen a un cambio ya ocurrido, por lo que el problema es de publicación y suscripción, no de comportamiento interno.

### Necesidad 4 — Reglas de transición según el estado
**Patrón aplicado: State.** Cada estado tiene un objeto (`EstadoPendiente`, `EstadoAprobada`, `EstadoEjecutada`, etc.) que define qué operaciones son válidas. `GestorEstadoSolicitud` delega en el estado actual y actualiza el objeto de estado después de una transición válida. Así se evita concentrar todas las reglas en if/else dispersos.

**Alternativa descartada: Strategy.** Aunque ambos patrones encapsulan comportamiento, Strategy supone que un cliente externo selecciona entre algoritmos intercambiables. Aquí la propia solicitud determina qué comportamiento corresponde a partir de su estado actual y las transiciones cambian ese estado.

## Reflexión — otros tres patrones
1. Recorrer solicitudes de un centro de costo sin exponer la estructura de almacenamiento: **Iterator**.
2. Comprobantes con el mismo esqueleto de impresión pero distinto cuerpo: **Template Method**.
3. Guardar y restaurar instantáneas completas del estado de una solicitud sin exponer sus detalles: **Memento**. A diferencia de Command, que registra acciones ejecutables/deshacibles, Memento conserva estados internos completos.

## Conclusiones
La actividad permitió diferenciar patrones que pueden parecer similares por su estructura. Chain of Responsibility resuelve la delegación entre niveles, mientras Command encapsula operaciones reversibles e historial. Observer desacopla a los módulos que reaccionan ante cambios y State concentra las reglas según el estado actual del objeto. La comparación con Strategy y las demás alternativas muestra que la intención del patrón es más importante que su forma superficial.

## Herramientas utilizadas
- Java 17
- Spring Boot 3.2
- Apache Maven
- JUnit 5
- Git y GitHub
- VS Code o IntelliJ IDEA
