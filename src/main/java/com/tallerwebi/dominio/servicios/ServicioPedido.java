package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import org.springframework.web.multipart.MultipartFile;

public interface ServicioPedido {
    void guardar(Pedido pedidoNuevo);
    Pedido guardar(Archivo archivo);

    Archivo validarArchivo(MultipartFile file, Long idUsuario);

    Pedido realizarPedido(String nombre, String tipoPedido, Archivo archivoA, Archivo archivoB, Archivo archivoC, Long idUsuario);
}
