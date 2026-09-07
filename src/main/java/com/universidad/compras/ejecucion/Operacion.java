package com.universidad.compras.ejecucion;

public interface Operacion {
    void ejecutar();
    void deshacer();
    String getDescripcion();
}
