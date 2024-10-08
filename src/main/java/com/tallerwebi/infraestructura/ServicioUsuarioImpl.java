package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepciones.ContrasenasDistintas;
import com.tallerwebi.dominio.excepciones.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepciones.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepciones.UsuarioExistente;
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
    public Usuario registrar(DatosUsuarioRegistro datosRegistro) throws UsuarioExistente, NombreDeUsuarioRepetido {
        Usuario usuarioEncontrado = repositorioUsuario.buscarPorEmail(datosRegistro.getEmail());
        Usuario usuarioEncontradoPorNombreDeUsuario = repositorioUsuario.buscarPorNombreDeUsuario(datosRegistro.getNombre());

        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        if(usuarioEncontradoPorNombreDeUsuario != null){
            throw new NombreDeUsuarioRepetido();
        }
        if(!datosRegistro.getPassword().equals(datosRegistro.getC_password())){
            throw new ContrasenasDistintas();
        }

        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setEmail(datosRegistro.getEmail());
        usuarioNuevo.setUsername(datosRegistro.getUsername());
        usuarioNuevo.setPassword(datosRegistro.getPassword());
        usuarioNuevo.setNombre(datosRegistro.getNombre());
        usuarioNuevo.setApellido(datosRegistro.getApellido());
        usuarioNuevo.setRol("CLIENTE");

        repositorioUsuario.guardar(usuarioNuevo);

        return usuarioNuevo;
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email){

        return repositorioUsuario.buscarPorEmail(email);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        return repositorioUsuario.buscarPorId(idUsuario);
    }

    private static boolean noSeEncontroUsuario(Usuario usuarioBuscado) {
        return usuarioBuscado != null;
    }

    @Override
    public void modificarUsuario(Usuario usuario){
        repositorioUsuario.modificar(usuario);
    }
}
