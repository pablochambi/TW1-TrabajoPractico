package com.tallerwebi.dominio;

public interface RepositorioUsuario1 {
    Usuario buscar(String email);

    void guardar(Usuario usuarioCreado);
}
