package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioLogin1;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioLogin1Impl implements ServicioLogin1 {

    @Override
    public Usuario consultarUsuario(String email, String password) {
        return null;
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {

    }
}
