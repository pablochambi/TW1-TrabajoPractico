package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.presentacion.DatosUsuarioRegistro;
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

        Usuario usuarioBuscado = repositorioUsuario.buscarPorEmail(email);

        if(noSeEncontroUsuario(usuarioBuscado)){ return null; }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);

        repositorioUsuario.guardar(usuario);

        return usuario;
    }

    @Override
    public void registrar(DatosUsuarioRegistro datosRegistro) throws UsuarioExistente, NombreDeUsuarioRepetido {
        Usuario usuarioEncontrado = repositorioUsuario.buscarPorEmail(datosRegistro.getEmail());
        Usuario usuarioEncontradoPorNombreDeUsuario = repositorioUsuario.buscarPorNombreDeUsuario(datosRegistro.getNombre());

        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        if(usuarioEncontradoPorNombreDeUsuario != null){
            throw new NombreDeUsuarioRepetido();
        }

        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setEmail(datosRegistro.getEmail());
        usuarioNuevo.setUsername(datosRegistro.getUsername());
        usuarioNuevo.setPassword(datosRegistro.getPassword());
        usuarioNuevo.setNombre(datosRegistro.getNombre());
        usuarioNuevo.setApellido(datosRegistro.getApellido());

        repositorioUsuario.guardar(usuarioNuevo);

    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email){

        return repositorioUsuario.buscarPorEmail(email);
    }

    private static boolean noSeEncontroUsuario(Usuario usuarioBuscado) {
        return usuarioBuscado != null;
    }
}
