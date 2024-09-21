package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.DatosUsuarioRegistro;

public interface ServicioUsuario {

    Usuario registrar(String email, String password);
    Usuario registrar(DatosUsuarioRegistro datosUsuarioRegistro) throws UsuarioExistente, NombreDeUsuarioRepetido;
    Usuario buscarUsuarioPorEmail(String email);

    Usuario buscarUsuarioPorId(Long idUsuario);
}
