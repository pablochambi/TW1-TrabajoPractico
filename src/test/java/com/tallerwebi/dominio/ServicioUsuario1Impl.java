package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicioUsuario1Impl implements ServicioUsuario1 {

    private RepositorioUsuario1 repositorioUsuario1;

    @Autowired
    public ServicioUsuario1Impl(RepositorioUsuario1 repositorioUsuario1) {
        this.repositorioUsuario1 = repositorioUsuario1;
    }

    @Override
    public Usuario registrar(String email, String password)  {
        if(password.length()<5){
            throw new PasswordLongitudIncorrecta();
        }

        Usuario usuarioBuscado = repositorioUsuario1.buscar(email);

        if(noSeEncontroUsuario(usuarioBuscado)){ return null; }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);

        repositorioUsuario1.guardar(usuario);

        return usuario;
    }

    private static boolean noSeEncontroUsuario(Usuario usuarioBuscado) {
        return usuarioBuscado != null;
    }
}
