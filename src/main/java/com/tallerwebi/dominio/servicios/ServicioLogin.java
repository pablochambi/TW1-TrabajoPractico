package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepciones.UsuarioExistente;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    //void registrar(DatosUsuarioRegistro datosRegistro) throws UsuarioExistente;

}
