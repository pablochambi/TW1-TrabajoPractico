package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface ServicioAdministrador {
    List<Usuario> obtenerUsuariosClientes();
    List<Pedido> obtenerPedidosDelUsuario(Long id);

    Pedido obtenerPedidoPorID(Long id);

    List<Archivo> obtenerArchivos(Pedido pedidoEncontrado);

    Archivo buscarArchivo(Long id);
}
