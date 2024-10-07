package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.MensajeDTO;

import java.util.List;

public interface RepositorioMensaje {
    void guardar(Mensaje nuevoMensaje);

    List<Mensaje> obtenerMensajes(Usuario admin, Usuario cliente);
}
