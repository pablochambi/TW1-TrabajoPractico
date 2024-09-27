package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioArchivo {
    void guardar(Archivo archivo);
    List<Archivo> buscarPorIdDeUsuario(Long idUsuario);
}
