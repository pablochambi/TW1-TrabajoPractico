package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepciones.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepciones.UsuarioExistente;
import com.tallerwebi.presentacion.DatosUsuarioRegistro;

public interface ServicioUsuario {

    Usuario registrar(String email, String password);
    Usuario registrar(DatosUsuarioRegistro datosUsuarioRegistro) throws UsuarioExistente, NombreDeUsuarioRepetido;
    Usuario buscarUsuarioPorEmail(String email);

    Usuario buscarUsuarioPorId(Long idUsuario);

    void modificarUsuario(Usuario usuario);
}
