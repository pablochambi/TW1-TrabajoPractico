package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.repositorios.RepositorioMensaje;
import com.tallerwebi.dominio.servicios.ServicioMensajeria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioMensajeriaImpl implements ServicioMensajeria {

    private final RepositorioMensaje repositorioMensaje;

    @Autowired
    public ServicioMensajeriaImpl(RepositorioMensaje repositorioMensaje) {
        this.repositorioMensaje = repositorioMensaje;
    }

    @Override
    public Mensaje guardar(Mensaje nuevoMensaje) {

        if(nuevoMensaje != null)
            repositorioMensaje.guardar(nuevoMensaje);

        return nuevoMensaje;
    }
}
