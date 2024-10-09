package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServicioPedido {
    void guardar(Pedido pedidoNuevo);

    Archivo validarArchivo(MultipartFile file, Long idUsuario);

    Pedido realizarPedido(String nombre, String tipoPedido, Archivo archivoA, Archivo archivoB, Archivo archivoC, Long idUsuario);

    List<Pedido> obtenerPedidosVencidos();

    List<Pedido> obtenerPedidosVencidosPorUsuario(Usuario usuario);
}
