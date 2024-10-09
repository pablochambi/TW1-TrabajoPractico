package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface ServicioNotificacion {

    void verificarPedidosVencidos();
    void enviarNotificacionUsuario(Usuario usuario);

    List<Notificacion> obtenerNotificacionesPedido(Pedido pedido);
}
