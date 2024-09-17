package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("serviciousuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario registrar(String email, String password)  {
        if(password.length()<5){
            throw new PasswordLongitudIncorrecta();
        }

        Usuario usuarioBuscado = repositorioUsuario.buscarUsuarioPorEmail(email);

        if(noSeEncontroUsuario(usuarioBuscado)){ return null; }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);

        repositorioUsuario.guardar(usuario);

        return usuario;
    }

    private static boolean noSeEncontroUsuario(Usuario usuarioBuscado) {
        return usuarioBuscado != null;
    }
}
