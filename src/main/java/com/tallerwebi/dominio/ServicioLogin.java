package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.DatosUsuarioRegistro;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente;
    //void registrar(DatosUsuarioRegistro datosRegistro) throws UsuarioExistente;

}
