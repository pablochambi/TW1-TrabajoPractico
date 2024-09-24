package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPedido {
     void guardar(Pedido pedido);
     void eliminar(Long id);

     void modificarEstado(Long id, Estado estado);
     Pedido buscar(Long id);
     List<Pedido> buscarPorEstado(Estado estado);
}
