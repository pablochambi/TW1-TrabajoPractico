package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioAdministrador {
    List<Usuario> obtenerUsuarios();
    List<Pedido> obtenerPedidosDelUsuario(Long id);

    Pedido obtenerPedido(Long id);

    List<Archivo> obtenerArchivosDelPedido(Pedido pedido);

    Archivo obtenerArchivo(Long id);
}
