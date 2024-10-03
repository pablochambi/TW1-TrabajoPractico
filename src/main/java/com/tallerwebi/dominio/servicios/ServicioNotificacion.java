package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Usuario;

public interface ServicioNotificacion {

    void verificarPedidosVencidos();
    void enviarNotificacionUsuario(Usuario usuario);
}
