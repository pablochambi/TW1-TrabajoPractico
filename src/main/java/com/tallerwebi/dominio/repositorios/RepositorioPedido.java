package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Estado;
import com.tallerwebi.dominio.Pedido;

import java.util.List;

public interface RepositorioPedido {
     void guardar(Pedido pedido);

     void eliminar(Long id);

     Pedido buscar(Long id);

     List<Pedido> buscarPorEstado(Estado estado);

     void modificarEstado(Long id, Estado estado);
}
