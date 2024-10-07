package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioMensaje;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioMensajeria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioMensajeriaImpl implements ServicioMensajeria {

    private final RepositorioMensaje repositorioMensaje;
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioMensajeriaImpl(RepositorioMensaje repositorioMensaje, RepositorioUsuario repositorioUsuario) {
        this.repositorioMensaje = repositorioMensaje;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Mensaje guardar(Mensaje nuevoMensaje) {
        if(nuevoMensaje != null)
            repositorioMensaje.guardar(nuevoMensaje);

        return nuevoMensaje;
    }

    @Override
    public List<Mensaje> obtenerMensajes(Long adminId, Long clienteId) {

        if(clienteId != null && adminId != null ){
            Usuario admin = repositorioUsuario.buscarPorId(adminId);
            Usuario cliente = repositorioUsuario.buscarPorId(clienteId);

            return repositorioMensaje.obtenerMensajes(admin,cliente);
        }

        return List.of();
    }
}
