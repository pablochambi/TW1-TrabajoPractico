package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;

import java.util.List;

public interface RepositorioArchivo {
    void guardar(Archivo archivo);
    List<Archivo> buscarPorIdDeUsuario(Long idUsuario);
}
