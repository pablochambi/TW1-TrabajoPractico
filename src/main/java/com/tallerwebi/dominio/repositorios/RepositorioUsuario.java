package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Usuario;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    void modificar(Usuario usuario);
    Usuario buscarPorEmail(String email);
    Usuario buscarPorNombreDeUsuario(String nombre);

    Usuario buscarPorId(Long idUsuario);
}

