package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioArchivo {
    void guardar(Archivo archivo);
    List<Archivo> buscarPorIdDeUsuario(Long idUsuario);

    void eliminar(Archivo archivo);

    Archivo buscarPorId(Long archivoId);

    String getNombrePorID(Long archivoId);
}
