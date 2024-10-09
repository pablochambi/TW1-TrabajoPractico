package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioAdministrador;
import com.tallerwebi.dominio.repositorios.RepositorioArchivo;
import com.tallerwebi.dominio.repositorios.RepositorioPedido;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioAdministradorImpl implements RepositorioAdministrador {
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioPedido repositorioPedido;
    private RepositorioArchivo repositorioArchivo;

    @Autowired
    public RepositorioAdministradorImpl(SessionFactory sessionFactory, RepositorioUsuario repositorioUsuario, RepositorioPedido repositorioPedido, RepositorioArchivo repositorioArchivo) {
        this.sessionFactory = sessionFactory;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioPedido = repositorioPedido;
        this.repositorioArchivo = repositorioArchivo;
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return repositorioUsuario.obtenerUsuariosClientes();
    }

    @Override
    public List<Pedido> obtenerPedidosDelUsuario(Long id) {
        Usuario usuario = repositorioUsuario.buscarPorId(id);
        return repositorioPedido.buscarPorUsuario(usuario);
    }

    @Override
    public Pedido obtenerPedido(Long id) {
        return repositorioPedido.buscar(id);
    }

    @Override
    public List<Archivo> obtenerArchivosDelPedido(Pedido pedido) {
        return repositorioArchivo.buscarPorPedido(pedido);
    }

    @Override
    public Archivo obtenerArchivo(Long id) {
        return repositorioArchivo.buscarPorId(id);
    }


}
