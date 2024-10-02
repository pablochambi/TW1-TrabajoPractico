package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ServicioArchivo {
    void registrar(Archivo archivo);

    List<Archivo> buscarArchivosPorIdDeUsuario(Long idUsuario);

    void eliminarPorId(Long archivoId) throws IOException;

    String getNombreArchivoPorID(Long archivoId);

    void guardarEnCarpeta(MultipartFile file);

    void guardar(MultipartFile archivo, Usuario usuario) throws IOException;
    boolean noEsExtencionValida(MultipartFile archivo);

}
