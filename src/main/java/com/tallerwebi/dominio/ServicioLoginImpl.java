package com.tallerwebi.dominio;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.DatosUsuarioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(DatosUsuarioRegistro datosRegistro) throws UsuarioExistente {

        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(datosRegistro.getEmail(), datosRegistro.getPassword());

        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }

        //Aqui se tiene que crear un usuario: new Usuario si es que no se repitio el usuario
//        Usuario usuarioNuevo = new Usuario();
//        usuarioNuevo.setEmail(datosRegistro.getEmail());
//        usuarioNuevo.setNombre(datosRegistro.getNombre());
//        usuarioNuevo.setPassword(datosRegistro.getPassword());
        //repositorioUsuario.guardar(usuarioNuevo);
    }

}

