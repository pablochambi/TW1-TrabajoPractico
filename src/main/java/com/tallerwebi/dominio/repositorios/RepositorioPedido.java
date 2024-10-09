package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Estado;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioPedido {
     void guardar(Pedido pedido);

     void eliminar(Long id);

     Pedido buscar(Long id);

     List<Pedido> buscarPorEstado(Estado estado);

     void modificarEstado(Long id, Estado estado);

     List<Pedido> obtenerPedidosVencidos();

     List<Pedido> obtenerPedidosVencidosPorUsuario(Usuario usuario);
}
