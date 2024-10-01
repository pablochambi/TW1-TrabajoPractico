package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

public interface RepositorioArchivo {
    void guardar(Archivo archivo);
    List<Archivo> buscarPorIdDeUsuario(Usuario usuario);

    void eliminar(Archivo archivo);

    Archivo buscarPorId(Long archivoId);

    String getNombrePorID(Long archivoId);
}
