package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Mensaje;

import java.util.List;

public interface ServicioMensajeria {

    Mensaje guardar(Mensaje nuevoMensaje);

    List<Mensaje> obtenerMensajes(Long adminId, Long clienteId);
}
