package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Archivo;

import java.util.List;

public interface ServicioArchivo {

    void registrar(Archivo archivo);

    List<Archivo> buscarArchivosPorIdDeUsuario(Long idUsuario);

    void eliminarPorId(Long archivoId);

    String getNombreArchivoPorID(Long archivoId);

}
