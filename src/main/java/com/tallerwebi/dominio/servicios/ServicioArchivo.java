package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ServicioArchivo {
    void registrar(Archivo archivo);

    List<Archivo> buscarArchivosPorIdDeUsuario(Long idUsuario);

    void eliminarPorId(Long archivoId,Long usuarioId) throws IOException;

    String getNombreArchivoPorID(Long archivoId);

    void guardarEnCarpeta(MultipartFile file, Long usuario_id);

    Archivo guardar(MultipartFile file, Long usuario_id) throws IOException;

    boolean noEsExtencionValida(MultipartFile file);

    List<Archivo> buscarArchivosPorPedido(Pedido pedido);
}
