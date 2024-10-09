package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioAdministrador;
import com.tallerwebi.dominio.servicios.ServicioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioAdministradorImpl  implements ServicioAdministrador {

    RepositorioAdministrador repositorioAdministrador;

    @Autowired
    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador){
        this.repositorioAdministrador = repositorioAdministrador;
    }

    @Override
    public List<Usuario> obtenerUsuariosClientes() {
        return repositorioAdministrador.obtenerUsuarios();
    }

    @Override
    public List<Pedido> obtenerPedidosDelUsuario(Long id) {
        return this.repositorioAdministrador.obtenerPedidosDelUsuario(id);
    }

    @Override
    public Pedido obtenerPedidoPorID(Long id) {
        return this.repositorioAdministrador.obtenerPedido(id);
    }

    @Override
    public List<Archivo> obtenerArchivos(Pedido pedido) {
        return repositorioAdministrador.obtenerArchivosDelPedido(pedido);
    }

    @Override
    public Archivo buscarArchivo(Long id) {
        return repositorioAdministrador.obtenerArchivo(id);
    }
}
