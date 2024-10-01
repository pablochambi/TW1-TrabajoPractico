package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepciones.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("serviciologin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {

        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());

        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }

        //Aqui se tiene que crear un usuario: new Usuario si es que no se repitio el usuario
//        Usuario usuarioNuevo = new Usuario();
//        usuarioNuevo.setEmail(datosRegistro.getEmail());
//        usuarioNuevo.setNombre(datosRegistro.getNombre());
//        usuarioNuevo.setPassword(datosRegistro.getPassword());
//
//        repositorioUsuario.guardar(usuarioNuevo);

    }

}
