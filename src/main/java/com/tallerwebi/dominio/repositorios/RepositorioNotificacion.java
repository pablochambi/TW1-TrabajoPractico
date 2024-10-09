package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioNotificacion {

    void guardar(Notificacion notificacion);

    boolean existeNotificacionPedido(Pedido pedido);

    List<Notificacion> obtenerNotificacionesPedido(Pedido pedido);
}
