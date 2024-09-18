package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosUsuarioRegistro;

public interface ServicioUsuario {
    Usuario registrar(String email, String password);

    void registrar(DatosUsuarioRegistro datosUsuarioRegistro);
}
